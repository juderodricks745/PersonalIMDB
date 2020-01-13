package com.davidbronn.personalimdb.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Jude on 13/January/2020
 */
class SearchMoviesViewModel : ViewModel() {

    val moviesText = MutableLiveData<String>().apply { value = "" }

    fun fetchMovies() {

    }
}