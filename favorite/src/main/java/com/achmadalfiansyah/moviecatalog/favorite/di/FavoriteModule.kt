package com.achmadalfiansyah.moviecatalog.favorite.di

import com.achmadalfiansyah.moviecatalog.favorite.ui.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel {
        FavoriteViewModel(get())
    }
}