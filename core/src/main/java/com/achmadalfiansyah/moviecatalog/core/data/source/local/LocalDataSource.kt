package com.achmadalfiansyah.moviecatalog.core.data.source.local

import com.achmadalfiansyah.moviecatalog.core.data.source.local.entity.MovieEntity
import com.achmadalfiansyah.moviecatalog.core.data.source.local.entity.TvShowEntity
import com.achmadalfiansyah.moviecatalog.core.data.source.local.room.MovieDao
import com.achmadalfiansyah.moviecatalog.core.data.source.local.room.TvShowDao
import com.achmadalfiansyah.moviecatalog.core.util.SortUtils
import com.achmadalfiansyah.moviecatalog.core.util.SortUtils.MOVIE_ENTITY
import com.achmadalfiansyah.moviecatalog.core.util.SortUtils.TV_SHOW_ENTITY
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao, private val tvShowDao: TvShowDao
) {
    fun getMovieList(sort: String):
            Flow<List<MovieEntity>> =
        movieDao.getMovieList(SortUtils.getSortedQuery(sort, MOVIE_ENTITY))

    fun getTvShowList(sort: String):
            Flow<List<TvShowEntity>> =
        tvShowDao.getTvShowList(SortUtils.getSortedQuery(sort, TV_SHOW_ENTITY))

    fun getMovieDetail(movieId: Int?):
            Flow<MovieEntity> = movieDao.getMovieDetail(movieId)

    fun getTvShowDetail(tvShowId: Int?):
            Flow<TvShowEntity> = tvShowDao.getTvShowDetail(tvShowId)
    fun getSearchMovieList(query: String):
            Flow<List<MovieEntity>> =
        movieDao.getSearchMovies(query)
    fun getSearchTvShowList(query: String):
            Flow<List<TvShowEntity>> =
        tvShowDao.getSearchTvShows(query)

    fun getFavoriteMovieList(): Flow<List<MovieEntity>> =
        movieDao.getFavoriteMovie(true)

    fun getFavoriteTvShowList(): Flow< List<TvShowEntity>> =
        tvShowDao.getFavoriteTvShow(true)

    fun insertMovieList(movieEntities: List<MovieEntity>) =
        movieDao.insertMovieList(movieEntities)

    fun insertMovieDetail(movieEntities: MovieEntity) =
        movieDao.insertMovieDetail(movieEntities)

    fun insertTvShowList(tvShowEntities: List<TvShowEntity>) =
        tvShowDao.insertTvShowList(tvShowEntities)

    fun insertTvShowDetail(tvShowEntities: TvShowEntity) =
        tvShowDao.insertTvShowDetail(tvShowEntities)

    fun setFavoriteMovie(movieEntity: MovieEntity,isFavorite: Boolean) {
        movieEntity.isFavorite = isFavorite
        movieDao.updateMovie(movieEntity)
    }

    fun setFavoriteTvShow(tvShowEntity: TvShowEntity,isFavorite : Boolean) {
        tvShowEntity.isFavorite = isFavorite
        tvShowDao.updateTvShow(tvShowEntity)
    }

}