package com.achmadalfiansyah.moviecatalog.data.source.remote

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.achmadalfiansyah.moviecatalog.data.source.local.entity.MovieEntity
import com.achmadalfiansyah.moviecatalog.data.source.local.entity.TvShowEntity
import com.achmadalfiansyah.moviecatalog.vo.Resource

interface IRemoteDataSource {
    fun getMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>>
    fun getTvShows(sort: String): LiveData<Resource<PagedList<TvShowEntity>>>
}