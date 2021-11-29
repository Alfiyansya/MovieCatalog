package com.achmadalfiansyah.moviecatalog.core.domain.usecase

import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow
import com.achmadalfiansyah.moviecatalog.vo.Resource
import kotlinx.coroutines.flow.Flow

interface ShowUseCase {
    fun getAllMovie(sort: String): Flow<Resource<List<Movie>>>
    fun getAllTvShow(sort: String): Flow<Resource<List<TvShow>>>
}