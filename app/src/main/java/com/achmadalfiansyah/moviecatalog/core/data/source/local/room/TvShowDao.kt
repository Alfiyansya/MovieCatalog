package com.achmadalfiansyah.moviecatalog.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import com.achmadalfiansyah.moviecatalog.core.data.source.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TvShowDao {
    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getTvShowList(query: SimpleSQLiteQuery): Flow<List<TvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowList(TvShowEntity: List<TvShowEntity>)


}