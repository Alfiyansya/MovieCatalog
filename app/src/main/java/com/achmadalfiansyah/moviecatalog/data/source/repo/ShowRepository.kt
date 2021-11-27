package com.achmadalfiansyah.moviecatalog.data.source.repo

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.achmadalfiansyah.moviecatalog.data.source.local.ILocalDataSource
import com.achmadalfiansyah.moviecatalog.data.source.local.LocalDataSource
import com.achmadalfiansyah.moviecatalog.data.source.local.entity.MovieEntity
import com.achmadalfiansyah.moviecatalog.data.source.local.entity.TvShowEntity
import com.achmadalfiansyah.moviecatalog.data.source.remote.IRemoteDataSource
import com.achmadalfiansyah.moviecatalog.data.source.remote.NetworkBoundResource
import com.achmadalfiansyah.moviecatalog.data.source.remote.RemoteDataSource
import com.achmadalfiansyah.moviecatalog.data.source.remote.response.ApiResponse
import com.achmadalfiansyah.moviecatalog.data.source.remote.response.Movie
import com.achmadalfiansyah.moviecatalog.data.source.remote.response.TvShow
import com.achmadalfiansyah.moviecatalog.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

class ShowRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: ILocalDataSource,
    private val coroutineScope: CoroutineScope
) :
    IRemoteDataSource {
    override fun getMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<Movie>>(coroutineScope) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovieList(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): LiveData<ApiResponse<List<Movie>>> =
                remoteDataSource.getMovies()

            override suspend fun saveCallResult(data: List<Movie>?) {
                val movieList = ArrayList<MovieEntity>()
                if (data != null) {
                    for (response in data) {
                        val movie = MovieEntity(
                            id = response.id.toString(),
                            title = response.title,
                            genres = "",
                            overview = response.overview,
                            imagePath = response.posterPath.toString(),
                            rating = response.voteAverage
                        )
                        movieList.add(movie)
                    }
                }
                localDataSource.insertMovieList(movieList)
            }

        }.asLiveData()
    }

    override fun getTvShows(sort: String): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object :
            NetworkBoundResource<PagedList<TvShowEntity>, List<TvShow>>(coroutineScope) {
            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getTvShowList(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): LiveData<ApiResponse<List<TvShow>>> {
                return remoteDataSource.getTvShows()
            }

            override suspend fun saveCallResult(data: List<TvShow>?) {
                val tvShowList = ArrayList<TvShowEntity>()
                if (data != null) {
                    for (response in data) {
                        val tvShow = TvShowEntity(
                            id = response.id.toString(),
                            name = response.name,
                            genres = "",
                            overview = response.overview,
                            imagePath = response.posterPath.toString(),
                            rating = response.voteAverage
                        )
                        tvShowList.add(tvShow)
                    }
                }
                localDataSource.insertTvShowList(tvShowList)
            }

        }.asLiveData()
    }
}