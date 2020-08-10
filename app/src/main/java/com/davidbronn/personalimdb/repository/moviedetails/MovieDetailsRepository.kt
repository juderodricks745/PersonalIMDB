package com.davidbronn.personalimdb.repository.moviedetails

import com.davidbronn.personalimdb.models.database.LikedMovie
import com.davidbronn.personalimdb.models.network.MovieCastItem
import com.davidbronn.personalimdb.models.network.MovieDetails
import com.davidbronn.personalimdb.utils.misc.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Jude on 12/January/2020
 */
interface MovieDetailsRepository {

    fun insertMovie(movieId: Int)
    fun deleteMovie(movieId: Int)
    fun checkIfLikedMovie(movieId: Int): LikedMovie?
    fun fetchMovieDetails(movieId: Int): Flow<Resource<MovieDetails>>
    fun fetchMoviesCast(movieId: Int): Flow<Resource<List<MovieCastItem>>>
    fun fetchSimilarMovies(movieId: Int): Flow<Resource<List<MovieCastItem>>>
}