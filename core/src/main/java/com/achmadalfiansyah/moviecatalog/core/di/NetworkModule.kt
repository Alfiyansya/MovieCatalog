package com.achmadalfiansyah.moviecatalog.core.di

import com.achmadalfiansyah.moviecatalog.core.data.source.remote.api.ApiBuilder
import com.achmadalfiansyah.moviecatalog.core.data.source.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideNetworking(): ApiService {
        return ApiBuilder.createService()
    }
}