package com.achmadalfiansyah.moviecatalog.util

import com.achmadalfiansyah.moviecatalog.core.data.source.local.entity.MovieEntity
import com.achmadalfiansyah.moviecatalog.core.data.source.local.entity.TvShowEntity
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.response.MovieDetailResponse
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.response.MovieResponse
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.response.TvShowDetailResponse
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.response.TvShowResponse
import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow

object DataMapper {
    const val MOVIE = "Movie"
    const val TVSHOW = "Tv Show"
    const val BEST_VOTE = "Best Vote"
    const val WORST_VOTE = "Worst Vote"
    const val RANDOM_VOTE = "Random Vote"
    const val IMAGE_ENDPOINT = "https://image.tmdb.org/t/p/w500/"

}