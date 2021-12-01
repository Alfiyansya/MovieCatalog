package com.achmadalfiansyah.moviecatalog.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow
import com.achmadalfiansyah.moviecatalog.core.domain.usecase.ShowUseCase

class FavoriteViewModel(private val showUseCase: ShowUseCase) : ViewModel() {
    fun getFavoriteMovie() : LiveData<List<Movie>> = showUseCase.getFavoriteMovie().asLiveData()
    fun getFavoriteTvShow() : LiveData<List<TvShow>> = showUseCase.getFavoriteTvShow().asLiveData()
}