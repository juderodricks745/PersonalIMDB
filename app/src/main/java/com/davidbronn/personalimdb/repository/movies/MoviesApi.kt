package com.davidbronn.personalimdb.repository.movies

import com.davidbronn.personalimdb.models.network.MovieItem
import com.davidbronn.personalimdb.repository.movies.MoviesApi.ApiKeys.KEY_MOVIE_PAGE
import com.davidbronn.personalimdb.repository.movies.MoviesApi.ApiKeys.MOVIE_POPULAR
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Jude on 04/January/2020
 */
interface MoviesApi {

    @GET(MOVIE_POPULAR)
    suspend fun fetchPopularMoviesAsync(@Query(KEY_MOVIE_PAGE) pageNumber: Int):
            Response<MovieItem>

    object ApiKeys {
        const val KEY_MOVIE_PAGE = "page"
        const val MOVIE_POPULAR = "movie/popular"
    }
}