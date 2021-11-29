package com.achmadalfiansyah.moviecatalog.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.achmadalfiansyah.moviecatalog.core.data.source.repo.ShowRepository
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow
import com.achmadalfiansyah.moviecatalog.core.domain.usecase.ShowUseCase
import com.achmadalfiansyah.moviecatalog.vo.Resource

class TvShowViewModel (private val showUseCase : ShowUseCase) : ViewModel() {
    fun getTvShows(sort : String) : LiveData<Resource<List<TvShow>>> = showUseCase.getAllTvShow(sort).asLiveData()
}