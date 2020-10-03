package com.davidbronn.personalimdb.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.search.SearchRepositoryImpl
import kotlinx.coroutines.flow.Flow

class SearchViewModel @ViewModelInject constructor(private val repo: SearchRepositoryImpl) :
    ViewModel() {

    private var currentQueryValue: String? = null
    var currentSearchResult: Flow<PagingData<ResultsItem>>? = null

    fun searchMovies(queryString: String): Flow<PagingData<ResultsItem>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult = repo.searchMovies(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}