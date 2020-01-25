package com.davidbronn.personalimdb.ui.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.utils.misc.ErrorState
import com.davidbronn.personalimdb.utils.misc.NetworkState

class MoviesListViewModel(private val factory: MoviesDataSourceFactory) : ViewModel(),
    ErrorState.ErrorStateRetryListener {

    private var movies: LiveData<PagedList<ResultsItem?>>

    private val _errorState = ErrorState(this as ErrorState.ErrorStateRetryListener)
    val errorState = MutableLiveData<ErrorState>().apply { value = _errorState }

    val loading = MutableLiveData<Boolean>().apply { value = false }

    val bottomBarVisible =
        MutableLiveData<Boolean>().apply { value = false } // If loading is not done
    val bottomLoading = MutableLiveData<Boolean>().apply { value = false }
    val bottomErrorMessage = MutableLiveData<String>().apply { value = "" }
    val bottomErrorVisible = MutableLiveData<Boolean>().apply { value = false }

    init {
        val pageConfig = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .build()
        movies = LivePagedListBuilder<Int, ResultsItem>(factory, pageConfig)
            .build()
    }

    fun getMovies(): LiveData<PagedList<ResultsItem?>>? = movies

    fun loadingInitial(): LiveData<NetworkState>? = factory.dataSource.initial()

    fun loadingAfter(): LiveData<NetworkState>? = factory.dataSource.after()

    fun setInitialLoading(loading: Boolean) {
        this.loading.value = loading
        errorState.value = _errorState.copy(isLoading = loading, isError = false)
    }

    fun setInitialError(message: String) {
        errorState.value =
            _errorState.copy(isLoading = false, isError = true, errorMessage = message)
    }

    fun setBottomLoading(loading: Boolean) {
        bottomLoading.value = loading
        bottomBarVisible.value = loading
        bottomErrorVisible.value = false
    }

    fun setBottomError(message: String) {
        bottomLoading.value = false
        bottomBarVisible.value = true
        bottomErrorVisible.value = true
        bottomErrorMessage.value = message
    }

    override fun onRetry() {
        factory.dataSource.retryFetch()
    }
}
