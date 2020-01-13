package com.davidbronn.personalimdb.repository.moviedetails

import com.davidbronn.personalimdb.BuildConfig
import com.davidbronn.personalimdb.models.network.MovieCast
import com.davidbronn.personalimdb.models.network.MovieDetails
import com.davidbronn.personalimdb.models.network.MovieItem
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Jude on 12/January/2020
 */
interface MovieDetailsApi {

    @GET("/3/movie/{movie_id}?api_key=${BuildConfig.API_KEY}")
    fun fetchMovieDetailsAsync(@Path("movie_id") movieId: Int): Deferred<Response<MovieDetails>>

    @GET("/3/movie/{movie_id}/similar?api_key=${BuildConfig.API_KEY}")
    fun fetchSimilarMoviesAsync(@Path("movie_id") movieId: Int) : Deferred<Response<MovieItem>>

    @GET("/3/movie/{movie_id}/credits?api_key=${BuildConfig.API_KEY}")
    fun fetchMoviesCastAsync(@Path("movie_id") movieId: Int) : Deferred<Response<MovieCast>>
}