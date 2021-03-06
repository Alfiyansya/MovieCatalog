package com.achmadalfiansyah.moviecatalog.core.domain.repository

import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow
import com.achmadalfiansyah.moviecatalog.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IShowRepository {
    fun getMovies(sort: String): Flow<Resource<List<Movie>>>
    fun getTvShows(sort: String): Flow<Resource<List<TvShow>>>
    fun getMovieDetail(id : Int): Flow<Resource<Movie>>
    fun getTvShowDetail(id : Int?): Flow<Resource<TvShow>>
    fun getSearchMovies(query: String): Flow<Resource<List<Movie>>>
    fun getSearchTvShows(query: String): Flow<Resource<List<TvShow>>>
    fun getFavoriteMovie(): Flow<List<Movie>>
    fun setFavoriteMovie(movie: Movie, isFavorite: Boolean)
    fun getFavoriteTvShow(): Flow<List<TvShow>>
    fun setFavoriteTvShow(tvShow: TvShow, isFavorite: Boolean)

}