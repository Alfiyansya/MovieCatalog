package com.achmadalfiansyah.moviecatalog.core.di

import android.content.Context
import androidx.room.Room
import com.achmadalfiansyah.moviecatalog.core.data.source.local.room.MovieDao
import com.achmadalfiansyah.moviecatalog.core.data.source.local.room.ShowDatabase
import com.achmadalfiansyah.moviecatalog.core.data.source.local.room.TvShowDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): ShowDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("alfiansyah".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context,
            ShowDatabase::class.java,
            "show.db"
        )
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideMovieDao(database: ShowDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    fun provideTvShowDao(database: ShowDatabase): TvShowDao {
        return database.tvShowDao()
    }
}