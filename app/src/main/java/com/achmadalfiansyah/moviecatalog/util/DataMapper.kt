package com.achmadalfiansyah.moviecatalog.util

import com.achmadalfiansyah.moviecatalog.core.data.source.local.entity.MovieEntity
import com.achmadalfiansyah.moviecatalog.core.data.source.local.entity.TvShowEntity
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.response.MovieResponse
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
//    fun mapDomainToEntity(input: Movie) = MovieEntity(
//        id = input.tourismId,
//        description = input.description,
//        name = input.name,
//        address = input.address,
//        latitude = input.latitude,
//        longitude = input.longitude,
//        like = input.like,
//        image = input.image,
//        isFavorite = input.isFavorite
//    )
}