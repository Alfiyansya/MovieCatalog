package com.achmadalfiansyah.moviecatalog.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TvShowViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is TvShow Fragment"
    }
    val text: LiveData<String> = _text
}