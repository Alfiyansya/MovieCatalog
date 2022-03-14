package com.achmadalfiansyah.moviecatalog.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val id: Int,
    val title: String?,
    val overview: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null
)
