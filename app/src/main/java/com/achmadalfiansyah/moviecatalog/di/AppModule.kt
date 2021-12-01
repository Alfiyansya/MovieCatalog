package com.achmadalfiansyah.moviecatalog.di

import com.achmadalfiansyah.moviecatalog.core.domain.usecase.ShowInteractor
import com.achmadalfiansyah.moviecatalog.core.domain.usecase.ShowUseCase
import com.achmadalfiansyah.moviecatalog.ui.adapter.MovieAdapter
import com.achmadalfiansyah.moviecatalog.ui.adapter.TvShowAdapter
import com.achmadalfiansyah.moviecatalog.ui.detail.DetailViewModel
import com.achmadalfiansyah.moviecatalog.ui.favorite.FavoriteViewModel
import com.achmadalfiansyah.moviecatalog.ui.movie.MovieViewModel
import com.achmadalfiansyah.moviecatalog.ui.tvshow.TvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val showViewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}
val adapterModule = module {
    factory { MovieAdapter() }
    factory { TvShowAdapter() }
}
val useCaseModule = module {
    factory<ShowUseCase> { ShowInteractor(get()) }
}

