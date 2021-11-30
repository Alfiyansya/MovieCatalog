package com.achmadalfiansyah.moviecatalog.core.data.source.local.room

import androidx.lifecycle.LiveData

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.achmadalfiansyah.moviecatalog.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @RawQuery(observedEntities = [MovieEntity::class])
    fun getMovieList(query: SimpleSQLiteQuery): Flow<List<MovieEntity>>

    @Query("SElECT * FROM movieentity WHERE movieId = :movieId")
    fun getMovieDetail(movieId: Int?): Flow<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieList(movieEntity: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetail(movieEntity: MovieEntity)


}