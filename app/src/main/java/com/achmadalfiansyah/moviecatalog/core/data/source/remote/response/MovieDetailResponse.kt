package com.achmadalfiansyah.moviecatalog.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

class MovieDetailResponse(
    val id: Int,
    val title: String,
    val genres: List<Genre>,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
)