package com.achmadalfiansyah.moviecatalog.core.di

import com.achmadalfiansyah.moviecatalog.core.data.source.repo.ShowRepository
import com.achmadalfiansyah.moviecatalog.core.domain.repository.IShowRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(showRepository: ShowRepository): IShowRepository

}