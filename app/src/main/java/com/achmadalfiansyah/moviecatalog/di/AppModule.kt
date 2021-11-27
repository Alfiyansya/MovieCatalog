package com.achmadalfiansyah.moviecatalog.di

import android.content.Context
import androidx.room.Room
import com.achmadalfiansyah.moviecatalog.data.source.local.ILocalDataSource
import com.achmadalfiansyah.moviecatalog.data.source.local.LocalDataSource
import com.achmadalfiansyah.moviecatalog.data.source.local.room.MovieDao
import com.achmadalfiansyah.moviecatalog.data.source.local.room.ShowDatabase
import com.achmadalfiansyah.moviecatalog.data.source.local.room.TvShowDao
import com.achmadalfiansyah.moviecatalog.data.source.remote.RemoteDataSource
import com.achmadalfiansyah.moviecatalog.data.source.remote.api.ApiBuilder
import com.achmadalfiansyah.moviecatalog.data.source.remote.api.ApiService
import com.achmadalfiansyah.moviecatalog.data.source.repo.ShowRepository
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
val remoteModule = module {
    single {
        RemoteDataSource(get())
    }
}
val repositoryModule = module {
    single {
        ShowRepository(get(), get(), get())
    }
}
val dataSourceModule = module {
    fun provideShowLocalDataSource(movieDao: MovieDao, tvShowDao: TvShowDao): ILocalDataSource {
        return LocalDataSource(movieDao, tvShowDao)
    }
    single { provideShowLocalDataSource(get(), get()) }

}
val databaseModule = module {
    fun provideAppDatabase(context: Context): ShowDatabase {
        return Room.databaseBuilder(
            context,
            ShowDatabase::class.java,
            "show.db"
        ).fallbackToDestructiveMigration().build()
    }

    fun provideMovieDao(database: ShowDatabase): MovieDao {
        return database.movieDao()
    }

    fun provideTvShowDao(database: ShowDatabase): TvShowDao {
        return database.tvShowDao()
    }
    single { provideAppDatabase(androidApplication()) }
    single { provideMovieDao(get()) }
    single { provideTvShowDao(get()) }
}

val networkModule = module {
    fun provideNetworking(): ApiService {
        return ApiBuilder.createService()
    }
    single { provideNetworking() }

}
val coroutineScopeModule = module {
    single { CoroutineScope(Dispatchers.IO) }
}