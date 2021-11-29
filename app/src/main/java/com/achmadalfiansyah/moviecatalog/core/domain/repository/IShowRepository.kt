package com.achmadalfiansyah.moviecatalog.core.domain.repository

import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow
import com.achmadalfiansyah.moviecatalog.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IShowRepository {
    fun getMovies(sort: String): Flow<Resource<List<Movie>>>
    fun getTvShows(sort: String): Flow<Resource<List<TvShow>>>
}