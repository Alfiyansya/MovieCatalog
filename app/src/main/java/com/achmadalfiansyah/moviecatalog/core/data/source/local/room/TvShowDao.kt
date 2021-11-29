package com.achmadalfiansyah.moviecatalog.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.achmadalfiansyah.moviecatalog.core.data.source.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TvShowDao {
    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getTvShowList(query: SimpleSQLiteQuery): Flow<List<TvShowEntity>>

    @Query("SElECT * FROM tvshowentity WHERE tvShowId = :tvShowId")
    fun getTvShowDetail(tvShowId: String): Flow<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowList(TvShowEntity: List<TvShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShowDetail(TvShowEntity: TvShowEntity)


}