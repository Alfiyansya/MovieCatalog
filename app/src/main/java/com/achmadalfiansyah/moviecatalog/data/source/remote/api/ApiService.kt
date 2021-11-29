package com.achmadalfiansyah.moviecatalog.data.source.remote.api

import com.achmadalfiansyah.moviecatalog.BuildConfig
import com.achmadalfiansyah.moviecatalog.data.source.remote.response.ListResponse
import com.achmadalfiansyah.moviecatalog.data.source.remote.response.Movie
import com.achmadalfiansyah.moviecatalog.data.source.remote.response.TvShow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    fun getMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Call<ListResponse<Movie>>

    @GET("discover/tv")
    fun getTvShows(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Call<ListResponse<TvShow>>

}