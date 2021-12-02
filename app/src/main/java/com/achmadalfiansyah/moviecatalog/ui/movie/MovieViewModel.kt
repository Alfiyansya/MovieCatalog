package com.achmadalfiansyah.moviecatalog.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie
import com.achmadalfiansyah.moviecatalog.core.domain.usecase.ShowUseCase
import com.achmadalfiansyah.moviecatalog.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val showUseCase: ShowUseCase) : ViewModel() {
    fun getMovies(sort : String) : LiveData<Resource<List<Movie>>> = showUseCase.getAllMovie(sort).asLiveData()
    fun getSearchMovie(query : String) : LiveData<Resource<List<Movie>>> = showUseCase.getSearchMovies(query).asLiveData()
}