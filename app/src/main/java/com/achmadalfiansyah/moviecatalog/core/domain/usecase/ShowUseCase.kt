package com.achmadalfiansyah.moviecatalog.core.domain.usecase

import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow
import com.achmadalfiansyah.moviecatalog.vo.Resource
import kotlinx.coroutines.flow.Flow

interface ShowUseCase {
    fun getAllMovie(sort: String): Flow<Resource<List<Movie>>>
    fun getAllTvShow(sort: String): Flow<Resource<List<TvShow>>>
    fun getMovieDetail(id: Int): Flow<Resource<Movie>>
    fun getTvShowDetail(id: Int): Flow<Resource<TvShow>>

    fun getSearchMovies(query: String): Flow<Resource<List<Movie>>>
    fun getSearchTvShows(query: String): Flow<Resource<List<TvShow>>>

    fun getFavoriteMovie(): Flow<List<Movie>>
    fun getFavoriteTvShow(): Flow<List<TvShow>>

    fun setFavoriteMovie(movie: Movie,state: Boolean)
    fun setFavoriteTvShow(tvShow: TvShow,state: Boolean)

}