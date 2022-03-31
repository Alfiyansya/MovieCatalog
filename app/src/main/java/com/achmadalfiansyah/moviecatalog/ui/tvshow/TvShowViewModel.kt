package com.achmadalfiansyah.moviecatalog.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow
import com.achmadalfiansyah.moviecatalog.core.domain.usecase.ShowUseCase
import com.achmadalfiansyah.moviecatalog.core.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(private val showUseCase : ShowUseCase) : ViewModel() {
    fun getTvShows(sort : String) : LiveData<Resource<List<TvShow>>> = showUseCase.getAllTvShow(sort).asLiveData()
    fun getSearchTvShows(query : String) : LiveData<Resource<List<TvShow>>> = showUseCase.getSearchTvShows(query).asLiveData()
}