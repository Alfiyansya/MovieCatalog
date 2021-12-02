package com.achmadalfiansyah.moviecatalog.core.data.source.repo

import com.achmadalfiansyah.moviecatalog.core.data.source.local.LocalDataSource
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.NetworkBoundResource
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.RemoteDataSource
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.response.*
import com.achmadalfiansyah.moviecatalog.core.di.ApplicationScope
import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow
import com.achmadalfiansyah.moviecatalog.core.domain.repository.IShowRepository
import com.achmadalfiansyah.moviecatalog.util.DataMapper
import com.achmadalfiansyah.moviecatalog.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    @ApplicationScope private val coroutineScope: CoroutineScope
) : IShowRepository {
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
                coroutineScope.launch(Dispatchers.IO) {
                    val movieList = DataMapper.mapMovieResponsesToEntities(data)
                    localDataSource.insertMovieList(movieList)
                }
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
                coroutineScope.launch(Dispatchers.IO) {
                    val tvShowList = DataMapper.mapTvShowResponsesToEntities(data)
                    localDataSource.insertTvShowList(tvShowList)
                }
            }

        }.asFlow()
    }

    override fun getMovieDetail(id: Int): Flow<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, MovieDetailResponse>() {
            override fun loadFromDB(): Flow<Movie> {
                return localDataSource.getMovieDetail(id).map {
                    DataMapper.mapMovieDetailEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: Movie?): Boolean {
                return data != null && data.genres == ""
            }

            override suspend fun createCall(): Flow<ApiResponse<MovieDetailResponse>> {
                return remoteDataSource.getMovieDetail(id)
            }

            override suspend fun saveCallResult(data: MovieDetailResponse) {
                coroutineScope.launch(Dispatchers.IO) {
                    val movie = DataMapper.mapMovieDetailResponsesToEntities(data)
                    localDataSource.insertMovieDetail(movie)
                }
            }

        }.asFlow()
    }

    override fun getTvShowDetail(id: Int?): Flow<Resource<TvShow>> {
        return object : NetworkBoundResource<TvShow, TvShowDetailResponse>() {
            override fun loadFromDB(): Flow<TvShow> {
                return localDataSource.getTvShowDetail(id).map {
                    DataMapper.mapTvShowDetailEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: TvShow?): Boolean {
                return data != null && data.genres == ""
            }

            override suspend fun createCall(): Flow<ApiResponse<TvShowDetailResponse>> {
                return remoteDataSource.getTvShowDetail(id)
            }

            override suspend fun saveCallResult(data: TvShowDetailResponse) {
                coroutineScope.launch(Dispatchers.IO) {
                    val tvShow = DataMapper.mapTvShowDetailResponsesToEntities(data)
                    localDataSource.insertTvShowDetail(tvShow)
                }
            }

        }.asFlow()
    }

    override fun getSearchMovies(query: String): Flow<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getSearchMovieList(query).map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
//                data == null || data.isEmpty()
                true
            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getSearchMovies(query)

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                coroutineScope.launch(Dispatchers.IO) {
                    val movieList = DataMapper.mapMovieResponsesToEntities(data)
                    localDataSource.insertMovieList(movieList)
                }
            }

        }.asFlow()
    }

    override fun getSearchTvShows(query: String): Flow<Resource<List<TvShow>>> {
        return object : NetworkBoundResource<List<TvShow>, List<TvShowResponse>>() {
            override fun loadFromDB(): Flow<List<TvShow>> {
                return localDataSource.getSearchTvShowList(query).map {
                    DataMapper.mapTvShowEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<TvShow>?): Boolean =
//                data == null || data.isEmpty()
                true
            override suspend fun createCall(): Flow<ApiResponse<List<TvShowResponse>>> =
                remoteDataSource.getSearchTvShows(query)

            override suspend fun saveCallResult(data: List<TvShowResponse>) {
                coroutineScope.launch(Dispatchers.IO) {
                    val tvShowList = DataMapper.mapTvShowResponsesToEntities(data)
                    localDataSource.insertTvShowList(tvShowList)
                }
            }

        }.asFlow()
    }

    override fun setFavoriteMovie(movie: Movie, isFavorite: Boolean) {
        coroutineScope.launch(Dispatchers.IO) {
            val movieEntity = DataMapper.mapMovieDomainToEntity(movie)
            localDataSource.setFavoriteMovie(movieEntity,isFavorite)
        }
    }

    override fun setFavoriteTvShow(tvShow: TvShow, isFavorite: Boolean) {
        coroutineScope.launch(Dispatchers.IO) {
            val tvShowEntity = DataMapper.mapTvShowDomainToEntity(tvShow)
            localDataSource.setFavoriteTvShow(tvShowEntity,isFavorite)
        }
    }

    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovieList().map {
            DataMapper.mapMovieEntitiesToDomain(it)

        }
    }

    override fun getFavoriteTvShow(): Flow<List<TvShow>> {
        return  localDataSource.getFavoriteTvShowList().map {
            DataMapper.mapTvShowEntitiesToDomain(it)
        }
    }
}