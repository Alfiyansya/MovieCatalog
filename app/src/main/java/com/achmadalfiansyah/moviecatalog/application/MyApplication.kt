@file:Suppress("unused")

package com.achmadalfiansyah.moviecatalog.application

import android.app.Application
//import com.achmadalfiansyah.moviecatalog.core.di.coroutineScopeModule
import com.achmadalfiansyah.moviecatalog.core.di.databaseModule
import com.achmadalfiansyah.moviecatalog.core.di.networkModule
import com.achmadalfiansyah.moviecatalog.core.di.repositoryModule
import com.achmadalfiansyah.moviecatalog.di.useCaseModule
import com.achmadalfiansyah.moviecatalog.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    repositoryModule,
                    networkModule,
                    databaseModule,
                    adapterModule,
                    useCaseModule,
                    showViewModelModule
                )
            )
        }
    }
}