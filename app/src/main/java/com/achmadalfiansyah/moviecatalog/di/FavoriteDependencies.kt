package com.achmadalfiansyah.moviecatalog.di

import com.achmadalfiansyah.moviecatalog.core.domain.usecase.ShowUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteDependencies {
    fun showUseCase() : ShowUseCase
}