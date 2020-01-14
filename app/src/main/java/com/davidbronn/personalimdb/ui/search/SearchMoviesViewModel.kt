package com.davidbronn.personalimdb.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.searchmovies.SearchMoviesRepository
import com.davidbronn.personalimdb.utils.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by Jude on 13/January/2020
 */
class SearchMoviesViewModel(private val repository: SearchMoviesRepository) : ViewModel() {

    val progress = MutableLiveData<Boolean>()
    val movieResults = MutableLiveData<List<ResultsItem?>>()

    /**
     * Fetch movies by the search text
     */
    fun fetchMovies(searchText: String) {
        progress.value = true
        viewModelScope.launch {
            delay(1000)
            when (val result = repository.fetchMovies(searchText, "true")) {
                is Result.Success -> {
                    progress.value = false
                    movieResults.value = result.data ?: Collections.emptyList()
                }
                is Result.Error -> {
                    progress.value = false
                }
                Result.Loading -> {
                    progress.value = false
                }
            }
        }
    }
}