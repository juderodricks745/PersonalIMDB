package com.davidbronn.personalimdb.ui.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.davidbronn.personalimdb.models.network.MovieItem
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.movieslist.MoviesListApi
import com.davidbronn.personalimdb.ui.annotations.MovieType
import com.davidbronn.personalimdb.utils.misc.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * Created by Jude on 05/January/2020
 */
class MoviesDataSource(
    private val type: Int,
    private val moviesApi: MoviesListApi
) : PageKeyedDataSource<Int, ResultsItem>() {

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    private val loadFirst = MutableLiveData<NetworkState>()
    private val loadAfter = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResultsItem>
    ) {
        try {
            loadFirst.startLoading()
            val c = fetchMoviesByType(type, 1)
            c.enqueue(object : Callback<MovieItem> {
                override fun onFailure(call: Call<MovieItem>, t: Throwable) {
                    retry = { loadInitial(params, callback) }
                    handleErrorStates(loadFirst, t)
                }

                override fun onResponse(
                    call: Call<MovieItem>,
                    response: Response<MovieItem>
                ) {
                    if (response.isSuccessful) {
                        retry = null
                        handleErrorStates(loadFirst, null)
                        callback.onResult(response.body()?.results!!, null, 2)
                    } else {
                        retry = { loadInitial(params, callback) }
                        handleErrorStates(loadFirst, null)
                    }
                }
            })
        } catch (e: Exception) {
            retry = { loadInitial(params, callback) }
            handleErrorStates(loadFirst, e)
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ResultsItem>
    ) {
        try {
            loadAfter.startLoading()
            val c = fetchMoviesByType(type, params.key)
            c.enqueue(object : Callback<MovieItem> {
                override fun onFailure(call: Call<MovieItem>, t: Throwable) {
                    retry = { loadAfter(params, callback) }
                    handleErrorStates(loadAfter, t)
                }

                override fun onResponse(call: Call<MovieItem>, response: Response<MovieItem>) {
                    if (response.isSuccessful) {
                        retry = null
                        handleErrorStates(loadFirst, null)
                        callback.onResult(response.body()?.results!!, params.key + 1)
                    } else {
                        retry = { loadAfter(params, callback) }
                        handleErrorStates(loadFirst, null)
                    }
                }
            })
        } catch (e: Exception) {
            retry = { loadAfter(params, callback) }
            handleErrorStates(loadAfter, e)
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ResultsItem>
    ) {
        // NOT USED
    }

    private fun fetchMoviesByType(type: Int, pageNumber: Int): Call<MovieItem> {
        return when (type) {
            MovieType.MOVIES_NOW_PLAYING -> moviesApi.fetchNowPlayingMoviesAsync(pageNumber)
            MovieType.MOVIES_POPULAR -> moviesApi.fetchPopularMoviesAsync(pageNumber)
            MovieType.MOVIES_TOP_RATED -> moviesApi.fetchTopRatedMoviesAsync(pageNumber)
            MovieType.MOVIES_UPCOMING -> moviesApi.fetchUpcomingMoviesAsync(pageNumber)
            else -> moviesApi.fetchLatestMoviesAsync(pageNumber)
        }
    }

    private fun MutableLiveData<NetworkState>.startLoading() {
        onUI { this.value = (NetworkState.Loading(true)) }
    }

    private fun MutableLiveData<NetworkState>.stopLoading() {
        onUI { this.value = (NetworkState.Loading(false)) }
    }

    private fun MutableLiveData<NetworkState>.showMessage(message: String) {
        onUI { this.value = (NetworkState.NetworkError(message)) }
    }

    private fun onUI(body: () -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            body()
        }
    }

    private fun handleErrorStates(
        liveData: MutableLiveData<NetworkState>,
        error: Throwable?
    ) {
        liveData.stopLoading()
        error?.let {
            Timber.e(it)
            liveData.showMessage(it.localizedMessage ?: "")
        }
    }

    fun retryFetch() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }

    fun initial(): LiveData<NetworkState> = loadFirst

    fun after(): LiveData<NetworkState> = loadAfter
}