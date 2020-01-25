package com.davidbronn.personalimdb.ui.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.davidbronn.personalimdb.models.network.MovieItem
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.movieslist.MoviesApi
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
    private val type: String,
    private val moviesApi: MoviesApi
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
                    loadFirst.stopLoading()
                    retry = { loadInitial(params, callback) }
                    loadFirst.showMessage(t.localizedMessage ?: "")
                }

                override fun onResponse(
                    call: Call<MovieItem>,
                    response: Response<MovieItem>
                ) {
                    if (response.isSuccessful) {
                        retry = null
                        loadFirst.stopLoading()
                        callback.onResult(response.body()?.results!!, null, 2)
                    } else {
                        loadFirst.stopLoading()
                        retry = { loadInitial(params, callback) }
                    }
                }
            })
        } catch (e: Exception) {
            Timber.e(e)
            loadFirst.stopLoading()
            retry = { loadInitial(params, callback) }
            loadFirst.showMessage(e.localizedMessage ?: "")
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
                    loadAfter.stopLoading()
                    retry = { loadAfter(params, callback) }
                    loadAfter.showMessage(t.localizedMessage ?: "")
                }

                override fun onResponse(call: Call<MovieItem>, response: Response<MovieItem>) {
                    if (response.isSuccessful) {
                        loadAfter.stopLoading()
                        callback.onResult(response.body()?.results!!, params.key + 1)
                    } else {
                        loadAfter.stopLoading()
                        retry = { loadAfter(params, callback) }
                    }
                }
            })
        } catch (e: Exception) {
            Timber.e(e)
            loadAfter.stopLoading()
            retry = { loadAfter(params, callback) }
            loadAfter.showMessage(e.localizedMessage ?: "")
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ResultsItem>
    ) {
        // NOT USED
    }

    private fun fetchMoviesByType(type: String, pageNumber: Int): Call<MovieItem> {
        return if (type == "T") {
            moviesApi.fetchTopRatedMoviesAsync(pageNumber)
        } else {
            moviesApi.fetchPopularMoviesAsync(pageNumber)
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

    fun retryFetch() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }

    fun initial(): LiveData<NetworkState> = loadFirst

    fun after(): LiveData<NetworkState> = loadAfter

    fun loadingState() = loadFirst
}