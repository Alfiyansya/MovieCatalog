package com.achmadalfiansyah.moviecatalog.di

import com.achmadalfiansyah.moviecatalog.core.domain.usecase.ShowInteractor
import com.achmadalfiansyah.moviecatalog.core.domain.usecase.ShowUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule{
    @Binds
    abstract fun provideShowUseCase(showInteractor: ShowInteractor) : ShowUseCase
}