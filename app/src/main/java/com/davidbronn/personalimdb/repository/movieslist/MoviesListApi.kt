package com.davidbronn.personalimdb.repository.movieslist

import com.davidbronn.personalimdb.models.network.MovieItem
import com.davidbronn.personalimdb.utils.misc.MovieConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Jude on 04/January/2020
 */
interface MoviesListApi {

    @GET(MovieConstants.MovieList.MOVIE_LATEST)
    fun fetchLatestMoviesAsync(@Query(MovieConstants.MovieList.KEY_MOVIE_PAGE) pageNumber: Int):
            Call<MovieItem>

    @GET(MovieConstants.MovieList.MOVIE_NOW_PLAYING)
    fun fetchNowPlayingMoviesAsync(@Query(MovieConstants.MovieList.KEY_MOVIE_PAGE) pageNumber: Int):
            Call<MovieItem>

    @GET(MovieConstants.MovieList.MOVIE_POPULAR)
    fun fetchPopularMoviesAsync(@Query(MovieConstants.MovieList.KEY_MOVIE_PAGE) pageNumber: Int):
            Call<MovieItem>

    @GET(MovieConstants.MovieList.MOVIE_TOP_RATED)
    fun fetchTopRatedMoviesAsync(@Query(MovieConstants.MovieList.KEY_MOVIE_PAGE) pageNumber: Int):
            Call<MovieItem>

    @GET(MovieConstants.MovieList.MOVIE_UPCOMING)
    fun fetchUpcomingMoviesAsync(@Query(MovieConstants.MovieList.KEY_MOVIE_PAGE) pageNumber: Int):
            Call<MovieItem>
}