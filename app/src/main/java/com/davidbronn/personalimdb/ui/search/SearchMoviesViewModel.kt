package com.davidbronn.personalimdb.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.searchmovies.SearchMoviesRepository
import com.davidbronn.personalimdb.utils.misc.Event
import com.davidbronn.personalimdb.utils.misc.Result
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by Jude on 13/January/2020
 */
class SearchMoviesViewModel @ViewModelInject constructor(private val repository: SearchMoviesRepository) : ViewModel() {

    val progress = MutableLiveData<Boolean>()

    private val _event = MutableLiveData<Event<SearchMoviesEvent>>()
    val event: LiveData<Event<SearchMoviesEvent>> = _event

    /**
     * Fetch movies by the search text
     */
    fun fetchMovies(searchText: String) {
        progress.value = true
        viewModelScope.launch {
            when (val result = repository.fetchMovies(searchText)) {
                is Result.Success -> {
                    progress.value = false
                    _event.value =
                        Event(SearchMoviesEvent.MoviesList(result.data ?: Collections.emptyList()))
                }
                is Result.Error -> {
                    progress.value = false
                    _event.value = Event(SearchMoviesEvent.SnackBar(result.exception.message ?: ""))
                }
                Result.Loading -> {
                    progress.value = false
                }
            }
        }
    }

    sealed class SearchMoviesEvent {
        data class SnackBar(val message: String) : SearchMoviesEvent()
        data class MoviesList(val moviesList: List<ResultsItem?>?) : SearchMoviesEvent()
    }
}