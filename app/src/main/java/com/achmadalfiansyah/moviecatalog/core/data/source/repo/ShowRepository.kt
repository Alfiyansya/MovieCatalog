package com.achmadalfiansyah.moviecatalog.core.data.source.repo

import com.achmadalfiansyah.moviecatalog.core.data.source.local.LocalDataSource
import com.achmadalfiansyah.moviecatalog.core.data.source.local.entity.MovieEntity
import com.achmadalfiansyah.moviecatalog.core.data.source.local.entity.TvShowEntity
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.NetworkBoundResource
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.RemoteDataSource
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.response.ApiResponse
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.response.MovieResponse
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.response.TvShowResponse
import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow
import com.achmadalfiansyah.moviecatalog.core.domain.repository.IShowRepository
import com.achmadalfiansyah.moviecatalog.util.DataMapper
import com.achmadalfiansyah.moviecatalog.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.ArrayList

class ShowRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) :
    IShowRepository {
    override fun getMovies(sort: String): Flow<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getMovieList(sort).map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getMovies()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMovieList(movieList)
            }

        }.asFlow()
    }


    override fun getTvShows(sort: String): Flow<Resource<List<TvShow>>> {
        return object :
            NetworkBoundResource<List<TvShow>, List<TvShowResponse>>() {
            override fun loadFromDB(): Flow<List<TvShow>> {
                return localDataSource.getTvShowList(sort).map {
                    DataMapper.mapTvShowEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<TvShow>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<TvShowResponse>>> {
                return remoteDataSource.getTvShows()
            }

            override suspend fun saveCallResult(data: List<TvShowResponse>) {
                val tvShowList = DataMapper.mapTvShowResponsesToEntities(data)
                localDataSource.insertTvShowList(tvShowList)


            }

        }.asFlow()
    }
}