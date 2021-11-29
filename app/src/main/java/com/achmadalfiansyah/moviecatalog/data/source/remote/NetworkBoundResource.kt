package com.achmadalfiansyah.moviecatalog.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.achmadalfiansyah.moviecatalog.data.source.remote.response.ApiResponse
import com.achmadalfiansyah.moviecatalog.data.source.remote.response.StatusResponse
import com.achmadalfiansyah.moviecatalog.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class NetworkBoundResource<ResultType, RequestType>(private val coroutineScope: CoroutineScope) {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                coroutineScope.launch {
                    fetchFromNetwork(dbSource)
                }
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Resource.success(newData)
                }
            }
        }
    }

    private fun onFetchFailed() {}
    protected abstract fun loadFromDB(): LiveData<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract suspend fun createCall(): LiveData<ApiResponse<RequestType>>
    protected abstract suspend fun saveCallResult(data: RequestType?)

    private suspend fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()

        coroutineScope.launch(Dispatchers.Main) {
            result.addSource(dbSource) { newData ->
                result.value = Resource.loading(newData)
            }
            result.addSource(apiResponse) { response ->
                result.removeSource(apiResponse)
                result.removeSource(dbSource)

                when (response.status) {
                    StatusResponse.SUCCESS ->
                        coroutineScope.launch(Dispatchers.IO) {
                            saveCallResult(response.body)

                            coroutineScope.launch(Dispatchers.Main) {
                                result.addSource(loadFromDB()) { newData ->
                                    result.value = Resource.success(newData)
                                }
                            }
                        }
                    StatusResponse.EMPTY ->
                        coroutineScope.launch(Dispatchers.Main) {
                            result.addSource(loadFromDB()) { newData ->
                                result.value = Resource.success(newData)
                            }
                        }
                    StatusResponse.ERROR -> {
                        onFetchFailed()
                        result.addSource(dbSource) { newData ->
                            result.value = Resource.error(response.message, newData)
                        }
                    }


                }
            }
        }

    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result
}