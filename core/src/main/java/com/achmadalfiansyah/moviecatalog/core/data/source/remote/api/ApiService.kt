package com.achmadalfiansyah.moviecatalog.core.data.source.remote.api

import com.achmadalfiansyah.moviecatalog.core.BuildConfig
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): ListResponse<MovieResponse>

    @GET("discover/tv")
    suspend fun getTvShows(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): ListResponse<TvShowResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") id: Int?,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): MovieDetailResponse

    @GET("tv/{tv_id}")
    suspend fun getTvShowDetail(
        @Path("tv_id") id: Int?,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY

    ): TvShowDetailResponse
    @GET("search/movie")
    suspend fun getSearchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ) : ListResponse<MovieResponse>
    @GET("search/tv")
    suspend fun getSearchTvs(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ) : ListResponse<TvShowResponse>
}