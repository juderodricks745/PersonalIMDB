package com.davidbronn.personalimdb.repository.moviedetails

import com.davidbronn.personalimdb.models.network.MovieCredit
import com.davidbronn.personalimdb.models.network.MovieDetails
import com.davidbronn.personalimdb.models.network.MovieItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Jude on 12/January/2020
 */
interface MovieDetailsApi {

    @GET(MOVIE_DETAILS)
    suspend fun fetchMovieDetailsAsync(@Path(KEY_MOVIE_ID) movieId: Int): Response<MovieDetails>

    @GET(MOVIE_SIMILAR)
    suspend fun fetchSimilarMoviesAsync(@Path(KEY_MOVIE_ID) movieId: Int): Response<MovieItem>

    @GET(MOVIE_CREDITS)
    suspend fun fetchMoviesCreditAsync(@Path(KEY_MOVIE_ID) movieId: Int): Response<MovieCredit>
    
    companion object ApiKeys {
        const val KEY_MOVIE_ID = "movie_id" // Key for fetching Movie by ID
        const val MOVIE_DETAILS = "/3/movie/{movie_id}"
        const val MOVIE_SIMILAR = "/3/movie/{movie_id}/similar"
        const val MOVIE_CREDITS = "/3/movie/{movie_id}/credits"
    }
}