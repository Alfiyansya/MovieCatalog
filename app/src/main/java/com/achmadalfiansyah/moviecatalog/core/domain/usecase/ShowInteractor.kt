package com.achmadalfiansyah.moviecatalog.core.domain.usecase

import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow
import com.achmadalfiansyah.moviecatalog.core.domain.repository.IShowRepository
import com.achmadalfiansyah.moviecatalog.vo.Resource
import kotlinx.coroutines.flow.Flow

class ShowInteractor(private val showRepository: IShowRepository) : ShowUseCase {
    override fun getAllMovie(sort : String) =
        showRepository.getMovies(sort)

    override fun getAllTvShow(sort : String) =
        showRepository.getTvShows(sort)

    override fun getMovieDetail(id: Int): Flow<Resource<Movie>> =
        showRepository.getMovieDetail(id)

    override fun getTvShowDetail(id: Int): Flow<Resource<TvShow>> =
        showRepository.getTvShowDetail(id)

}