@file:Suppress("unused")

package com.achmadalfiansyah.moviecatalog.application

import android.app.Application
import com.achmadalfiansyah.moviecatalog.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    remoteModule,
                    showViewModelModule,
                    repositoryModule,
                    networkModule,
                    databaseModule,
                    adapterModule,
                    coroutineScopeModule,
                    localDataSource
                )
            )
        }
    }
}