package com.achmadalfiansyah.moviecatalog.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow
import com.achmadalfiansyah.moviecatalog.core.domain.usecase.ShowUseCase
import com.achmadalfiansyah.moviecatalog.vo.Resource

class DetailViewModel constructor(private val showUseCase: ShowUseCase) : ViewModel(){
    fun getMovieDetail(id : Int) : LiveData<Resource<Movie>> = showUseCase.getMovieDetail(id).asLiveData()
    fun getTvShowDetail(id : Int) : LiveData<Resource<TvShow>> = showUseCase.getTvShowDetail(id).asLiveData()

}