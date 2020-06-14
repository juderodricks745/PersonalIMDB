package com.davidbronn.personalimdb.repository.movieslist

import androidx.paging.PagingSource
import com.davidbronn.personalimdb.models.network.MovieItem
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.ui.annotations.MovieType
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * Created by Jude on 14/June/2020
 */
class MoviesListPagingSource(
    private val type: Int,
    private val api: MoviesListApi
) : PagingSource<Int, ResultsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        val pageNumber = params.key ?: MOVIE_START_PAGE_INDEX
        return try {
            val response = fetchMoviesByType(pageNumber)
            val results = response.body()?.results
            LoadResult.Page(
                data = results!!,
                prevKey = null,
                nextKey = if (results.isEmpty()) null else pageNumber + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    private suspend fun fetchMoviesByType(pageNumber: Int): Response<MovieItem> {
        return when (type) {
            MovieType.MOVIES_NOW_PLAYING -> api.fetchNowPlayingMoviesAsync(pageNumber)
            MovieType.MOVIES_POPULAR -> api.fetchPopularMoviesAsync(pageNumber)
            MovieType.MOVIES_TOP_RATED -> api.fetchTopRatedMoviesAsync(pageNumber)
            MovieType.MOVIES_UPCOMING -> api.fetchUpcomingMoviesAsync(pageNumber)
            else -> api.fetchNowPlayingMoviesAsync(pageNumber)
        }
    }
}

private const val MOVIE_START_PAGE_INDEX = 1