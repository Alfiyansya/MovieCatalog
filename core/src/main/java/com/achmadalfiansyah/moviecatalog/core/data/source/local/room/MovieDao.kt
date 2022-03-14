package com.achmadalfiansyah.moviecatalog.core.data.source.local.room

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


    @Query("SELECT * FROM movieentity WHERE isFavorite = :isFavorite")
    fun getFavoriteMovie(isFavorite: Boolean): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movieentity WHERE title = :query")
    fun getSearchMovies(query: String): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieList(movieEntity: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetail(movieEntity: MovieEntity)
    @Update
    fun updateMovie(movieEntity: MovieEntity)


}