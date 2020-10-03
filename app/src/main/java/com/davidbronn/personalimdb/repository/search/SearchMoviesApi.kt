package com.davidbronn.personalimdb.repository.search

import com.davidbronn.personalimdb.models.network.MovieItem
import com.davidbronn.personalimdb.repository.search.SearchMoviesApi.ApiKeys.KEY_MOVIE_QUERY
import com.davidbronn.personalimdb.repository.search.SearchMoviesApi.ApiKeys.KEY_PAGE_NUMBER
import com.davidbronn.personalimdb.repository.search.SearchMoviesApi.ApiKeys.MOVIE_SEARCH
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Jude on 13/January/2020
 */
interface SearchMoviesApi {

    @GET(MOVIE_SEARCH)
    suspend fun fetchMoviesByTextAsync(
        @Query(KEY_MOVIE_QUERY) query: String,
        @Query(KEY_PAGE_NUMBER) pageNumber: Int
    ): Response<MovieItem>

    object ApiKeys {
        const val KEY_PAGE_NUMBER = "page"
        const val KEY_MOVIE_QUERY = "query"
        const val MOVIE_SEARCH = "/3/search/movie"
    }
}