package com.achmadalfiansyah.moviecatalog.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.achmadalfiansyah.moviecatalog.data.source.local.entity.MovieEntity

@Dao
interface MovieDao {
    @RawQuery(observedEntities = [MovieEntity::class])
    fun getMovieList(query: SimpleSQLiteQuery): DataSource.Factory<Int, MovieEntity>

    @Query("SElECT * FROM movieentity WHERE movieId = :movieId")
    fun getMovieDetail(movieId: String): LiveData<MovieEntity>

    @Query("SELECT * FROM movieentity WHERE isFavorite = :isFavorite")
    fun getFavoriteMovie(isFavorite: Boolean): DataSource.Factory<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieList(movieEntity: List<MovieEntity>)



}