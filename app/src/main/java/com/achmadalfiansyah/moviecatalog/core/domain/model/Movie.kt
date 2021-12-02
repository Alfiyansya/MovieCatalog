package com.achmadalfiansyah.moviecatalog.core.domain.model

data class Movie(
    val id: String,
    val title: String?,
    val genres: String?,
    val overview: String?,
    val imagePath: String?,
    val backdropPath: String? = null,
    val rating: Double?,
    var isFavorite: Boolean = false
)