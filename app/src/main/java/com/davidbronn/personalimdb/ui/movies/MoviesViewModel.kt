package com.davidbronn.personalimdb.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.movies.MoviesRepositoryImpl
import kotlinx.coroutines.flow.Flow

class MoviesViewModel @ViewModelInject constructor(private val repo: MoviesRepositoryImpl) :
    ViewModel() {

    fun fetchMovies(): Flow<PagingData<ResultsItem>> {
        return repo.fetchMovies().cachedIn(viewModelScope)
    }
}