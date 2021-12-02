package com.achmadalfiansyah.moviecatalog.di

import com.achmadalfiansyah.moviecatalog.core.domain.usecase.ShowInteractor
import com.achmadalfiansyah.moviecatalog.core.domain.usecase.ShowUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule{
    @Binds
    @ViewModelScoped
    abstract fun provideShowUseCase(showInteractor: ShowInteractor) : ShowUseCase
}