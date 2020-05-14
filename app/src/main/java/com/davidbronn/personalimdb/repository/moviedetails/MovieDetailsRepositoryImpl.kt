package com.davidbronn.personalimdb.repository.moviedetails

import com.davidbronn.personalimdb.models.database.LikedMovie
import com.davidbronn.personalimdb.models.network.CastItem
import com.davidbronn.personalimdb.models.network.MovieDetails
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.models.network.StatusModel
import com.davidbronn.personalimdb.utils.helpers.jsonify
import com.davidbronn.personalimdb.utils.misc.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Jude on 12/January/2020
 */
class MovieDetailsRepositoryImpl(private val api: MovieDetailsApi,
                                 private val dao: LikedMovieDao)
    : MovieDetailsRepository {

    override fun deleteMovie(movieId: kotlin.Int, movieStatus: Boolean) {
        dao.deleteMovie(LikedMovie(movieId, movieStatus))
    }

    override fun checkIfLikedMovie(movieId: kotlin.Int): LikedMovie? {
        return dao.checkLikedMovie(movieId)
    }

    override fun insertMovie(movieId: kotlin.Int, movieStatus: Boolean) {
        dao.insertMovie(LikedMovie(movieId, movieStatus))
    }

    override suspend fun fetchMoviesCast(movieId: kotlin.Int): Result<List<CastItem?>>? {
        return withContext(Dispatchers.IO) {
            val response = api.fetchMoviesCreditAsync(movieId).await()
            if (response.isSuccessful) {
                val movieBody = response.body()
                movieBody?.let {
                    return@withContext Result.Success(movieBody.cast!!)
                }
            } else {
                val statusModel = response.errorBody()?.string()?.jsonify<StatusModel>()
                Result.Error(RuntimeException(statusModel?.statusMessage ?: ""))
            }
        }
    }

    override suspend fun fetchSimilarMovies(movieId: kotlin.Int): Result<List<ResultsItem?>?>? {
        return withContext(Dispatchers.IO) {
            val response = api.fetchSimilarMoviesAsync(movieId).await()
            if (response.isSuccessful) {
                val movieBody = response.body()
                movieBody?.let {
                    return@withContext Result.Success(movieBody.results)
                }
            } else {
                val statusModel = response.errorBody()?.string()?.jsonify<StatusModel>()
                Result.Error(RuntimeException(statusModel?.statusMessage ?: ""))
            }
        }
    }

    override suspend fun getMovieDetails(movieId: kotlin.Int): Result<MovieDetails>? {
        return withContext(Dispatchers.IO) {
            val response = api.fetchMovieDetailsAsync(movieId).await()
            if (response.isSuccessful) {
                val movieBody = response.body()
                movieBody?.let {
                    return@withContext Result.Success(movieBody)
                }
            } else {
                val statusModel = response.errorBody()?.string()?.jsonify<StatusModel>()
                Result.Error(RuntimeException(statusModel?.statusMessage ?: ""))
            }
        }
    }
}