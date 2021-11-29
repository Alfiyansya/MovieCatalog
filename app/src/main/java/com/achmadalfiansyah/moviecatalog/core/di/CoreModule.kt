package com.achmadalfiansyah.moviecatalog.core.di

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
import com.achmadalfiansyah.moviecatalog.core.domain.repository.IShowRepository
import com.achmadalfiansyah.moviecatalog.core.domain.usecase.ShowInteractor
import com.achmadalfiansyah.moviecatalog.core.domain.usecase.ShowUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val repositoryModule = module {
    single {
        LocalDataSource(get(), get())
    }
    single {
        RemoteDataSource(get())
    }

    single<IShowRepository> {
        ShowRepository(get(), get())
    }
    factory { CoroutineScope(Dispatchers.IO) }
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
