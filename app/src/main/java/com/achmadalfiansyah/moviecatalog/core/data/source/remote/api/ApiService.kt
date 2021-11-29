package com.achmadalfiansyah.moviecatalog.core.data.source.remote.api

import com.achmadalfiansyah.moviecatalog.BuildConfig
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.response.ListResponse
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.response.MovieResponse
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.response.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    fun getMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Call<ListResponse<MovieResponse>>

    @GET("discover/tv")
    fun getTvShows(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Call<ListResponse<TvShowResponse>>

}