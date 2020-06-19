package com.davidbronn.personalimdb.ui.movieslist

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.movieslist.MoviesListRepository
import com.davidbronn.personalimdb.ui.movieslist.MoviesListViewModel.Keys.MOVIE_TYPE
import kotlinx.coroutines.flow.Flow

class MoviesListViewModel @ViewModelInject constructor(
    private val repo: MoviesListRepository,
    @Assisted private val handle: SavedStateHandle
) : ViewModel() {

    private var moviesResults: Flow<PagingData<ResultsItem>>? = null

    fun fetchMovies(): Flow<PagingData<ResultsItem>> {
        val newResult: Flow<PagingData<ResultsItem>> = repo.fetchMovies(handle.get<Int>(MOVIE_TYPE) ?: 0)
            .cachedIn(viewModelScope)
        moviesResults = newResult
        return newResult
    }

    object Keys {
        const val MOVIE_TYPE = "movie_type"
    }
}
