package com.achmadalfiansyah.moviecatalog.core.util

import com.achmadalfiansyah.moviecatalog.core.data.source.local.entity.MovieEntity
import com.achmadalfiansyah.moviecatalog.core.data.source.local.entity.TvShowEntity
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.response.MovieDetailResponse
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.response.MovieResponse
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.response.TvShowDetailResponse
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.response.TvShowResponse
import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow

object DataMapper {
    fun mapMovieResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val tourism = MovieEntity(
                id = it.id.toString(),
                title = it.title,
                genres = "",
                overview = it.overview,
                imagePath = it.posterPath.toString(),
                rating = it.voteAverage,
                isFavorite = false
            )
            movieList.add(tourism)
        }
        return movieList
    }
    fun mapMovieDetailResponsesToEntities(input: MovieDetailResponse?): MovieEntity {
        val genres = StringBuilder().append("")

        for (i in input?.genres?.indices!!) {
            if (i < input.genres.size - 1) {
                genres.append("${input.genres[i].name}, ")
            } else {
                genres.append(input.genres[i].name)
            }
        }

        return MovieEntity(
            id = input.id.toString(),
            title = input.title,
            genres = genres.toString(),
            overview = input.overview,
            imagePath = input.posterPath,
            rating = input.voteAverage,
            backdropPath = input.backdropPath,
            isFavorite = false
        )
    }

    fun mapMovieEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title  = it.title,
                genres = it.genres,
                overview = it.overview,
                imagePath = it.imagePath,
                rating = it.rating,
                backdropPath = it.backdropPath,
                isFavorite = it.isFavorite
            )
        }
    fun mapMovieDetailEntitiesToDomain(input: MovieEntity): Movie =
        Movie(
            id = input.id,
            title = input.title,
            genres = input.genres,
            overview = input.overview,
            imagePath = input.imagePath,
            rating = input.rating,
            backdropPath = input.backdropPath,
            isFavorite = input.isFavorite
        )
    fun mapTvShowResponsesToEntities(input: List<TvShowResponse>): List<TvShowEntity> {
        val tvShowList = ArrayList<TvShowEntity>()
        input.map {
            val tourism = TvShowEntity(
                id = it.id.toString(),
                name = it.name,
                genres = "",
                overview = it.overview,
                imagePath = it.posterPath.toString(),
                rating = it.voteAverage,
                isFavorite = false
            )
            tvShowList.add(tourism)
        }
        return tvShowList
    }
    fun mapTvShowDetailResponsesToEntities(input: TvShowDetailResponse?): TvShowEntity {
        val genres = StringBuilder().append("")

        for (i in input?.genres?.indices!!) {
            if (i < input.genres.size - 1) {
                genres.append("${input.genres[i].name}, ")
            } else {
                genres.append(input.genres[i].name)
            }
        }

        return TvShowEntity(
            id = input.id.toString(),
            name = input.name,
            genres = genres.toString(),
            overview = input.overview,
            imagePath = input.posterPath,
            rating = input.voteAverage,
            backdropPath = input.backdropPath,
            isFavorite = false
        )
    }

    fun mapTvShowEntitiesToDomain(input: List<TvShowEntity>): List<TvShow> =
        input.map {
            TvShow(
                id = it.id,
                name  = it.name,
                genres = it.genres,
                overview = it.overview,
                imagePath = it.imagePath.toString(),
                rating = it.rating,
                backdropPath = it.backdropPath,
                isFavorite = it.isFavorite
            )
        }

    fun mapTvShowDetailEntitiesToDomain(input: TvShowEntity): TvShow =
        TvShow(
            id = input.id,
            name  = input.name,
            genres = input.genres,
            overview = input.overview,
            imagePath = input.imagePath.toString(),
            rating = input.rating,
            backdropPath = input.backdropPath,
            isFavorite = input.isFavorite
        )

    fun mapMovieDomainToEntity(input: Movie) = MovieEntity(
        id = input.id,
        title = input.title,
        genres = input.genres,
        overview = input.overview,
        imagePath = input.imagePath,
        rating = input.rating,
        backdropPath = input.backdropPath,
        isFavorite = input.isFavorite
    )
    fun mapTvShowDomainToEntity(input: TvShow) = TvShowEntity(
        id = input.id,
        name = input.name,
        genres = input.genres,
        overview = input.overview,
        imagePath = input.imagePath,
        rating = input.rating,
        backdropPath = input.backdropPath,
        isFavorite = input.isFavorite
    )
}