package com.davidbronn.personalimdb.repository.searchmovies

import com.davidbronn.personalimdb.models.network.MovieItem
import com.davidbronn.personalimdb.utils.misc.MovieConstants
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Jude on 13/January/2020
 */
interface SearchMoviesApi {

    @GET(MovieConstants.MovieSearch.MOVIE_SEARCH)
    fun fetchMoviesByTextAsync(
        @Query(MovieConstants.MovieSearch.KEY_MOVIE_QUERY) query: String
    )
            : Deferred<Response<MovieItem>>
}