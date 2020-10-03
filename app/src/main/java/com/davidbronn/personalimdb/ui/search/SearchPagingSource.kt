package com.davidbronn.personalimdb.ui.search

import androidx.paging.PagingSource
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.search.SearchMoviesApi
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Jude on 07/September/2020
 */
private const val MOVIE_START_PAGE_INDEX = 1

class SearchPagingSource(
    private val queryString: String,
    private val api: SearchMoviesApi
) : PagingSource<Int, ResultsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        val pageNumber = params.key ?: MOVIE_START_PAGE_INDEX
        return try {
            val response = api.fetchMoviesByTextAsync(queryString, pageNumber)
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