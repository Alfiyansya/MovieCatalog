package com.achmadalfiansyah.moviecatalog.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.achmadalfiansyah.moviecatalog.data.source.local.entity.MovieEntity
import com.achmadalfiansyah.moviecatalog.data.source.repo.ShowRepository
import com.achmadalfiansyah.moviecatalog.vo.Resource

class MovieViewModel constructor(private val showRepo : ShowRepository) : ViewModel() {
    fun getMovies(sort : String) : LiveData<Resource<PagedList<MovieEntity>>> = showRepo.getMovies(sort)
}