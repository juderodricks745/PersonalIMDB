package com.davidbronn.personalimdb.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.searchmovies.SearchMoviesRepository
import com.davidbronn.personalimdb.utils.misc.AbsentLiveData
import com.davidbronn.personalimdb.utils.misc.Event
import com.davidbronn.personalimdb.utils.misc.Resource

/**
 * Created by Jude on 13/January/2020
 */
class SearchMoviesViewModel @ViewModelInject constructor(private val repository: SearchMoviesRepository) :
    ViewModel() {

    private val _event = MutableLiveData<Event<SearchMoviesEvent>>()
    val event: LiveData<Event<SearchMoviesEvent>> = _event

    private val searchMoviesText = MutableLiveData<String>().apply { value = "" }
    private val searchMoviesResults = searchMoviesText.switchMap { movieText ->
        if (movieText.isNullOrBlank()) {
            AbsentLiveData.create()
        } else {
            repository.fetchMoviesByLiveData(movieText)
        }
    }

    fun getResource(): LiveData<Resource<List<ResultsItem>>> = searchMoviesResults

    fun setMovieSearchParams(params: String) {
        if (searchMoviesText.value == params)
            return
        searchMoviesText.value = params
    }

    sealed class SearchMoviesEvent {
        data class SnackBar(val message: String) : SearchMoviesEvent()
    }
}