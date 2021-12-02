package com.achmadalfiansyah.moviecatalog.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvshowentity")
data class TvShowEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "tvShowId")
    val id: String,
    @ColumnInfo(name = "title")
    val name: String?,
    @ColumnInfo(name = "genres")
    val genres: String?,
    @ColumnInfo(name = "description")
    val overview: String?,
    @ColumnInfo(name = "imagePath")
    val imagePath: String?,
    @ColumnInfo(name = "backdropPath")
    val backdropPath: String? = null,
    @ColumnInfo(name = "rating")
    val rating: Double?,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)