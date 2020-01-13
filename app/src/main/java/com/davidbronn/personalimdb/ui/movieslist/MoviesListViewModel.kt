package com.davidbronn.personalimdb.ui.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.davidbronn.personalimdb.repository.movieslist.MoviesApi
import com.davidbronn.personalimdb.utils.Event
import androidx.paging.LivePagedListBuilder
import com.davidbronn.personalimdb.models.network.ResultsItem
import timber.log.Timber

class MoviesListViewModel(moviesApi: MoviesApi, type: String) : ViewModel() {

    private var moviesDataSource: LiveData<MoviesDataSource>? = null
    private var moviesLiveData: LiveData<PagedList<ResultsItem?>>? = null

    init {
        val pageConfig = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .build()
        val moviesFactory = MoviesDataSourceFactory(type, moviesApi)
        moviesDataSource = moviesFactory.movies()
        moviesLiveData = LivePagedListBuilder<Int, ResultsItem>(moviesFactory, pageConfig)
            .build()
    }

    fun getMovies(): LiveData<PagedList<ResultsItem?>>? = moviesLiveData
}
