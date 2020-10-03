package com.davidbronn.personalimdb.ui.movies

import androidx.paging.PagingSource
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.movies.MoviesApi
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Jude on 14/June/2020
 */
private const val MOVIE_START_PAGE_INDEX = 1

class MoviesPagingSource(
    private val api: MoviesApi
) : PagingSource<Int, ResultsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        val pageNumber = params.key ?: MOVIE_START_PAGE_INDEX
        return try {
            val response = api.fetchPopularMoviesAsync(pageNumber)
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
}