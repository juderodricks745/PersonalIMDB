package com.davidbronn.personalimdb.repository.movieslist

import com.davidbronn.personalimdb.BuildConfig
import com.davidbronn.personalimdb.models.network.MovieItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Jude on 04/January/2020
 */
interface MoviesApi {

    @GET("movie/popular?api_key=${BuildConfig.API_KEY}&sort_by=popularity.desc")
    fun fetchPopularMoviesAsync(@Query("page") pageNumber: Int): Call<MovieItem>

    @GET("movie/top_rated?api_key=${BuildConfig.API_KEY}&sort_by=popularity.desc")
    fun fetchTopRatedMoviesAsync(@Query("page") pageNumber: Int): Call<MovieItem>
}