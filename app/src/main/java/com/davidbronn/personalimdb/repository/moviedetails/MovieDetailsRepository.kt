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

    fun checkIfLikedMovie(movieId: kotlin.Int): LikedMovie?
    fun insertMovie(movieId: kotlin.Int, movieStatus: Boolean)
    fun deleteMovie(movieId: kotlin.Int, movieStatus: Boolean)
    suspend fun getMovieDetails(movieId: kotlin.Int): Result<MovieDetails>?
    suspend fun fetchMoviesCast(movieId: kotlin.Int): Result<List<CastItem?>>?
    suspend fun fetchSimilarMovies(movieId: kotlin.Int): Result<List<ResultsItem?>?>?
}