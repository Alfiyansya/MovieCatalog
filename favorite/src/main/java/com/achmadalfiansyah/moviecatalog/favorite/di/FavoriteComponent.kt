package com.achmadalfiansyah.moviecatalog.favorite.di

import android.content.Context
import com.achmadalfiansyah.moviecatalog.di.FavoriteDependencies
import com.achmadalfiansyah.moviecatalog.favorite.ui.FavoriteMovieAndTvShowFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteDependencies::class])
interface FavoriteComponent {

    fun inject(favoriteFragments: FavoriteMovieAndTvShowFragment)

    @Component.Builder
    interface Builder{
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteDependencies: FavoriteDependencies): Builder
        fun build(): FavoriteComponent
    }

}