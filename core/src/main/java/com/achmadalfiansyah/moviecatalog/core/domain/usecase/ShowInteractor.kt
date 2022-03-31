package com.achmadalfiansyah.moviecatalog.core.domain.usecase

import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie
import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow
import com.achmadalfiansyah.moviecatalog.core.domain.repository.IShowRepository
import com.achmadalfiansyah.moviecatalog.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowInteractor @Inject constructor(private val showRepository: IShowRepository) : ShowUseCase {
    override fun getAllMovie(sort: String) =
        showRepository.getMovies(sort)

    override fun getAllTvShow(sort: String) =
        showRepository.getTvShows(sort)

    override fun getMovieDetail(id: Int): Flow<Resource<Movie>> =
        showRepository.getMovieDetail(id)

    override fun getTvShowDetail(id: Int): Flow<Resource<TvShow>> =
        showRepository.getTvShowDetail(id)

    override fun getSearchMovies(query: String): Flow<Resource<List<Movie>>> =
        showRepository.getSearchMovies(query)

    override fun getSearchTvShows(query: String): Flow<Resource<List<TvShow>>> =
        showRepository.getSearchTvShows(query)

    override fun getFavoriteMovie(): Flow<List<Movie>> =
        showRepository.getFavoriteMovie()

    override fun getFavoriteTvShow(): Flow<List<TvShow>> =
        showRepository.getFavoriteTvShow()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) =
        showRepository.setFavoriteMovie(movie, state)

    override fun setFavoriteTvShow(tvShow: TvShow, state: Boolean) =
        showRepository.setFavoriteTvShow(tvShow, state)


}