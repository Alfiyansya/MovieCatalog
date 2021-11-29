package com.achmadalfiansyah.moviecatalog.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.achmadalfiansyah.moviecatalog.data.source.remote.api.ApiService
import com.achmadalfiansyah.moviecatalog.data.source.remote.response.ApiResponse
import com.achmadalfiansyah.moviecatalog.data.source.remote.response.Movie
import com.achmadalfiansyah.moviecatalog.data.source.remote.response.TvShow
import retrofit2.await

class RemoteDataSource(private val api: ApiService) {
    suspend fun getMovies(): LiveData<ApiResponse<List<Movie>>> {
        val resultMovie = MutableLiveData<ApiResponse<List<Movie>>>()
        try {
            val getData = api.getMovies().await().result
            resultMovie.postValue(ApiResponse.success(getData))
        } catch (e: Exception) {
            e.printStackTrace()
            resultMovie.postValue(ApiResponse.error(e.message.toString()))
        }
        return resultMovie
    }

    suspend fun getTvShows(): LiveData<ApiResponse<List<TvShow>>> {
        val resultTvShow = MutableLiveData<ApiResponse<List<TvShow>>>()
        try {
            val getData = api.getTvShows().await().result
            resultTvShow.postValue(ApiResponse.success(getData))
        } catch (e: Exception) {
            e.printStackTrace()
            resultTvShow.postValue(ApiResponse.error(e.message.toString()))
        }
        return resultTvShow
    }
}