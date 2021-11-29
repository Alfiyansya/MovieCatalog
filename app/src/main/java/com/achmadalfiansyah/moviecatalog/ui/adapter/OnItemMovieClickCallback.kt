package com.achmadalfiansyah.moviecatalog.ui.adapter

import com.achmadalfiansyah.moviecatalog.core.domain.model.Movie

interface OnItemMovieClickCallback {
    fun onItemClicked(movie : Movie?)
}