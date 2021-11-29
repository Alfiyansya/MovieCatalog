package com.achmadalfiansyah.moviecatalog.di

import android.content.Context
import androidx.room.Room
import com.achmadalfiansyah.moviecatalog.core.data.source.local.LocalDataSource
import com.achmadalfiansyah.moviecatalog.core.data.source.local.room.MovieDao
import com.achmadalfiansyah.moviecatalog.core.data.source.local.room.ShowDatabase
import com.achmadalfiansyah.moviecatalog.core.data.source.local.room.TvShowDao
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.RemoteDataSource
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.api.ApiBuilder
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.api.ApiService
import com.achmadalfiansyah.moviecatalog.core.data.source.repo.ShowRepository
import com.achmadalfiansyah.moviecatalog.core.domain.usecase.ShowInteractor
import com.achmadalfiansyah.moviecatalog.core.domain.usecase.ShowUseCase
import com.achmadalfiansyah.moviecatalog.ui.adapter.MovieAdapter
import com.achmadalfiansyah.moviecatalog.ui.adapter.TvShowAdapter
import com.achmadalfiansyah.moviecatalog.ui.movie.MovieViewModel
import com.achmadalfiansyah.moviecatalog.ui.tvshow.TvShowViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val showViewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
}
val adapterModule = module {
    factory { MovieAdapter() }
    factory { TvShowAdapter() }
}
val useCaseModule = module {
    factory<ShowUseCase> { ShowInteractor(get()) }
}

