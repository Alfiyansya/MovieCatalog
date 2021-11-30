package com.achmadalfiansyah.moviecatalog.core.data.source.local

import com.achmadalfiansyah.moviecatalog.core.data.source.local.entity.MovieEntity
import com.achmadalfiansyah.moviecatalog.core.data.source.local.entity.TvShowEntity
import com.achmadalfiansyah.moviecatalog.core.data.source.local.room.MovieDao
import com.achmadalfiansyah.moviecatalog.core.data.source.local.room.TvShowDao
import com.achmadalfiansyah.moviecatalog.util.SortUtils
import com.achmadalfiansyah.moviecatalog.util.SortUtils.MOVIE_ENTITY
import com.achmadalfiansyah.moviecatalog.util.SortUtils.TV_SHOW_ENTITY
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
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

    fun insertMovieList(movieEntities: List<MovieEntity>) =
        movieDao.insertMovieList(movieEntities)

    fun insertMovieDetail(movieEntities: MovieEntity) =
        movieDao.insertMovieDetail(movieEntities)

    fun insertTvShowList(tvShowEntities: List<TvShowEntity>) =
        tvShowDao.insertTvShowList(tvShowEntities)

    fun insertTvShowDetail(tvShowEntities: TvShowEntity) =
        tvShowDao.insertTvShowDetail(tvShowEntities)
}