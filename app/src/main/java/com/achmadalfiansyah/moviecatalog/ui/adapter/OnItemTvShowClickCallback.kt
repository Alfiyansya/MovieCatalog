package com.achmadalfiansyah.moviecatalog.ui.adapter

import com.achmadalfiansyah.moviecatalog.core.domain.model.TvShow

interface OnItemTvShowClickCallback {
    fun onItemClicked(tvShow : TvShow?)
}