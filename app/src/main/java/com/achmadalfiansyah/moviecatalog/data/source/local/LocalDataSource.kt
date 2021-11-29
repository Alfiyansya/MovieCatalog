package com.achmadalfiansyah.moviecatalog.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.achmadalfiansyah.moviecatalog.data.source.local.entity.MovieEntity
import com.achmadalfiansyah.moviecatalog.data.source.local.entity.TvShowEntity
import com.achmadalfiansyah.moviecatalog.data.source.local.room.MovieDao
import com.achmadalfiansyah.moviecatalog.data.source.local.room.TvShowDao
import com.achmadalfiansyah.moviecatalog.util.SortUtils
import com.achmadalfiansyah.moviecatalog.util.SortUtils.MOVIE_ENTITY
import com.achmadalfiansyah.moviecatalog.util.SortUtils.TV_SHOW_ENTITY

class LocalDataSource(
    private val movieDao: MovieDao, private val tvShowDao: TvShowDao
) {
    fun getMovieList(sort: String):
            DataSource.Factory<Int, MovieEntity> =
        movieDao.getMovieList(SortUtils.getSortedQuery(sort, MOVIE_ENTITY))

    fun getTvShowList(sort: String):
            DataSource.Factory<Int, TvShowEntity> =
        tvShowDao.getTvShowList(SortUtils.getSortedQuery(sort, TV_SHOW_ENTITY))

    fun insertMovieList(movieEntities: List<MovieEntity>) =
        movieDao.insertMovieList(movieEntities)

    fun insertTvShowList(tvShowEntities: List<TvShowEntity>) =
        tvShowDao.insertTvShowList(tvShowEntities)
}