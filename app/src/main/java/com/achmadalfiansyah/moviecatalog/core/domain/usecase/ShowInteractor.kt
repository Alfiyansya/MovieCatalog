package com.achmadalfiansyah.moviecatalog.core.domain.usecase

import com.achmadalfiansyah.moviecatalog.core.data.source.repo.ShowRepository
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
}