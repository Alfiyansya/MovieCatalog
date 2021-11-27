package com.achmadalfiansyah.moviecatalog.data.source.local

import androidx.paging.DataSource
import com.achmadalfiansyah.moviecatalog.data.source.local.entity.MovieEntity
import com.achmadalfiansyah.moviecatalog.data.source.local.entity.TvShowEntity

interface ILocalDataSource {
    fun getMovieList(sort: String): DataSource.Factory<Int, MovieEntity>
    fun getTvShowList(sort: String): DataSource.Factory<Int, TvShowEntity>
    suspend fun insertMovieList(movieEntities: List<MovieEntity>)
    suspend fun insertTvShowList(tvShowEntities: List<TvShowEntity>)

}