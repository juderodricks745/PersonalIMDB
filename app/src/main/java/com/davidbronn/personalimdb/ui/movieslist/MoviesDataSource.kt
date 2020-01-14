package com.davidbronn.personalimdb.ui.movieslist

import androidx.paging.PageKeyedDataSource
import com.davidbronn.personalimdb.models.network.MovieItem
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.movieslist.MoviesApi
import retrofit2.Response
import timber.log.Timber

/**
 * Created by Jude on 05/January/2020
 */
class MoviesDataSource(
    private val type: String,
    private val moviesApi: MoviesApi
) : PageKeyedDataSource<Int, ResultsItem>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResultsItem>
    ) {
        try {
            val response = fetchMoviesByType(type, 1)
            if (response.isSuccessful) {
                callback.onResult(response.body()?.results!!, null, 2)
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ResultsItem>
    ) {
        try {
            val response = fetchMoviesByType(type, params.key)
            if (response.isSuccessful) {
                callback.onResult(response.body()?.results!!, params.key + 1)
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ResultsItem>
    ) {
        // NOT USED
    }

    private fun fetchMoviesByType(type: String, pageNumber: Int): Response<MovieItem> {
        return if (type == "T") {
            moviesApi.fetchTopRatedMoviesAsync(pageNumber).execute()
        } else {
            moviesApi.fetchPopularMoviesAsync(pageNumber).execute()
        }
    }
}