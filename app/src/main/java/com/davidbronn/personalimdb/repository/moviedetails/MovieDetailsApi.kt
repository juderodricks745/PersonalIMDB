package com.davidbronn.personalimdb.repository.moviedetails

import com.davidbronn.personalimdb.models.network.MovieCredit
import com.davidbronn.personalimdb.models.network.MovieDetails
import com.davidbronn.personalimdb.models.network.MovieItem
import com.davidbronn.personalimdb.utils.misc.MovieConstants
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Jude on 12/January/2020
 */
interface MovieDetailsApi {

    @GET(MovieConstants.MovieDetails.MOVIE_DETAILS)
    fun fetchMovieDetailsAsync(@Path(MovieConstants.MovieDetails.KEY_MOVIE_ID) movieId: Int): Deferred<Response<MovieDetails>>

    @GET(MovieConstants.MovieDetails.MOVIE_SIMILAR)
    fun fetchSimilarMoviesAsync(@Path(MovieConstants.MovieDetails.KEY_MOVIE_ID) movieId: Int): Deferred<Response<MovieItem>>

    @GET(MovieConstants.MovieDetails.MOVIE_CREDITS)
    fun fetchMoviesCreditAsync(@Path(MovieConstants.MovieDetails.KEY_MOVIE_ID) movieId: Int): Deferred<Response<MovieCredit>>
}