package com.achmadalfiansyah.moviecatalog.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.achmadalfiansyah.moviecatalog.data.source.local.entity.TvShowEntity
import com.achmadalfiansyah.moviecatalog.data.source.repo.ShowRepository
import com.achmadalfiansyah.moviecatalog.vo.Resource

class TvShowViewModel constructor(private val showRepo : ShowRepository) : ViewModel() {
    fun getTvShows(sort : String) : LiveData<Resource<PagedList<TvShowEntity>>> = showRepo.getTvShows(sort)
}