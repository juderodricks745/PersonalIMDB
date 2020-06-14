package com.davidbronn.personalimdb.ui.movieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.movieslist.MoviesListRepository
import kotlinx.coroutines.flow.Flow

class MoviesListViewModel(private val repo: MoviesListRepository) : ViewModel() {

    private var moviesResults: Flow<PagingData<ResultsItem>>? = null

    fun fetchMovies(): Flow<PagingData<ResultsItem>> {
        val newResult: Flow<PagingData<ResultsItem>> = repo.fetchMovies()
            .cachedIn(viewModelScope)
        moviesResults = newResult
        return newResult
    }
}
