package com.achmadalfiansyah.moviecatalog.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow
import com.achmadalfiansyah.moviecatalog.core.domain.usecase.ShowUseCase
import com.achmadalfiansyah.moviecatalog.core.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val showUseCase: ShowUseCase) : ViewModel(){
    fun getMovieDetail(id : Int) : LiveData<Resource<Movie>> = showUseCase.getMovieDetail(id).asLiveData()
    fun getTvShowDetail(id : Int) : LiveData<Resource<TvShow>> = showUseCase.getTvShowDetail(id).asLiveData()

    fun setFavoriteMovie(movie: Movie,isFavorite: Boolean) =
        showUseCase.setFavoriteMovie(movie,isFavorite)
    fun setFavoriteTvShow(tvShow: TvShow, isFavorite: Boolean) =
        showUseCase.setFavoriteTvShow(tvShow,isFavorite)
}