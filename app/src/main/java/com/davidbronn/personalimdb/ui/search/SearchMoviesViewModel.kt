package com.davidbronn.personalimdb.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.searchmovies.SearchMoviesRepository
import com.davidbronn.personalimdb.utils.Result
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by Jude on 13/January/2020
 */
class SearchMoviesViewModel(private val repository: SearchMoviesRepository) : ViewModel() {

    val movieResults = MutableLiveData<List<ResultsItem?>>()

    /**
     * Fetch movies by the search text
     */
    fun fetchMovies(searchText: String) {
        viewModelScope.launch {
            when (val result = repository.fetchMovies(searchText, "true")) {
                is Result.Success -> {
                    movieResults.value = result.data ?: Collections.emptyList()
                }
                is Result.Error -> {

                }
                Result.Loading -> {

                }
            }
        }
    }
}