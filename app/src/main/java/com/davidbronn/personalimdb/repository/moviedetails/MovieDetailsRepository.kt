package com.davidbronn.personalimdb.repository.moviedetails

import com.davidbronn.personalimdb.models.database.LikedMovie
import com.davidbronn.personalimdb.models.network.CastItem
import com.davidbronn.personalimdb.models.network.MovieDetails
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.utils.misc.Result

/**
 * Created by Jude on 12/January/2020
 */
interface MovieDetailsRepository {

    fun checkIfLikedMovie(movieId: Int): LikedMovie?
    fun insertMovie(movieId: Int, movieStatus: Boolean)
    fun deleteMovie(movieId: Int, movieStatus: Boolean)
    suspend fun getMovieDetails(movieId: Int): Result<MovieDetails>?
    suspend fun fetchMoviesCast(movieId: Int) : Result<List<CastItem?>>?
    suspend fun fetchSimilarMovies(movieId: Int) : Result<List<ResultsItem?>?>?
}