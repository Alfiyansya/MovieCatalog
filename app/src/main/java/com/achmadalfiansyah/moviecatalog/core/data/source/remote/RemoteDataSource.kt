package com.achmadalfiansyah.moviecatalog.core.data.source.remote

import android.util.Log
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.api.ApiService
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val api: ApiService) {
    fun getMovies(): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = api.getMovies()
                val dataArray = response.result
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.result))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieDetail(id: Int): Flow<ApiResponse<MovieDetailResponse>> {
        return flow {
            try {
                val response = api.getMovieDetail(id)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvShows(): Flow<ApiResponse<List<TvShowResponse>>> {
        return flow {
            try {
                val response = api.getTvShows()
                val dataArray = response.result
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.result))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvShowDetail(id: Int?): Flow<ApiResponse<TvShowDetailResponse>> {
        return flow {
            try {
                val response = api.getTvShowDetail(id)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getSearchMovies(query : String): Flow<ApiResponse<List<MovieResponse>>> {
        return flow{
            try{
                val response = api.getSearchMovies(query)
                val resultArray = response.result
                if (resultArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.result))
                }else{
                    emit(ApiResponse.Empty)
                }

            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getSearchTvShows(query : String): Flow<ApiResponse<List<TvShowResponse>>> {
        return flow{
            try{
                val response = api.getSearchTvs(query)
                val resultArray = response.result
                if (resultArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.result))
                }else{
                    emit(ApiResponse.Empty)
                }

            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}