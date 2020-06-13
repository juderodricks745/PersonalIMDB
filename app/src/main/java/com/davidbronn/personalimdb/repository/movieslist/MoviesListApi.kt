package com.davidbronn.personalimdb.repository.movieslist

import com.davidbronn.personalimdb.models.network.MovieItem
import com.davidbronn.personalimdb.repository.movieslist.MoviesListApi.ApiKeys.KEY_MOVIE_PAGE
import com.davidbronn.personalimdb.repository.movieslist.MoviesListApi.ApiKeys.MOVIE_LATEST
import com.davidbronn.personalimdb.repository.movieslist.MoviesListApi.ApiKeys.MOVIE_NOW_PLAYING
import com.davidbronn.personalimdb.repository.movieslist.MoviesListApi.ApiKeys.MOVIE_POPULAR
import com.davidbronn.personalimdb.repository.movieslist.MoviesListApi.ApiKeys.MOVIE_TOP_RATED
import com.davidbronn.personalimdb.repository.movieslist.MoviesListApi.ApiKeys.MOVIE_UPCOMING
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Jude on 04/January/2020
 */
interface MoviesListApi {

    @GET(MOVIE_LATEST)
    fun fetchLatestMoviesAsync(@Query(KEY_MOVIE_PAGE) pageNumber: Int):
            Call<MovieItem>

    @GET(MOVIE_NOW_PLAYING)
    fun fetchNowPlayingMoviesAsync(@Query(KEY_MOVIE_PAGE) pageNumber: Int):
            Call<MovieItem>

    @GET(MOVIE_POPULAR)
    fun fetchPopularMoviesAsync(@Query(KEY_MOVIE_PAGE) pageNumber: Int):
            Call<MovieItem>

    @GET(MOVIE_TOP_RATED)
    fun fetchTopRatedMoviesAsync(@Query(KEY_MOVIE_PAGE) pageNumber: Int):
            Call<MovieItem>

    @GET(MOVIE_UPCOMING)
    fun fetchUpcomingMoviesAsync(@Query(KEY_MOVIE_PAGE) pageNumber: Int):
            Call<MovieItem>

    object ApiKeys {
        const val KEY_MOVIE_PAGE = "page"
        const val MOVIE_LATEST = "movie/latest"
        const val MOVIE_NOW_PLAYING = "movie/now_playing"
        const val MOVIE_POPULAR = "movie/popular"
        const val MOVIE_TOP_RATED = "movie/top_rated"
        const val MOVIE_UPCOMING = "movie/upcoming"
    }
}