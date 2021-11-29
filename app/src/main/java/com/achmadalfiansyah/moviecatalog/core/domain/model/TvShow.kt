package com.achmadalfiansyah.moviecatalog.core.domain.model

data class TvShow(
    val id: String,
    val name: String?,
    val genres: String,
    val overview: String,
    val imagePath: String,
    val backdropPath: String? = null,
    val rating: Double?,
    var isFavorite: Boolean = false
)