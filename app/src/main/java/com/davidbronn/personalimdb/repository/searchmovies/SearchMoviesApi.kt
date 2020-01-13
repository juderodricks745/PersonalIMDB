package com.davidbronn.personalimdb.repository.searchmovies

import com.davidbronn.personalimdb.BuildConfig
import com.davidbronn.personalimdb.models.network.MovieItem
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Jude on 13/January/2020
 */
interface SearchMoviesApi {

    @GET("/3/search/movie?api_key=${BuildConfig.API_KEY}")
    fun fetchMoviesByTextAsync(@Query("query") query: String,
                               @Query("include_adult") include_adult: String)
            : Deferred<Response<MovieItem>>
}