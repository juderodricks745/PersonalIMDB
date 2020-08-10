package com.davidbronn.personalimdb.repository.moviedetails

import com.davidbronn.personalimdb.models.database.LikedMovie
import com.davidbronn.personalimdb.models.network.MovieCastItem
import com.davidbronn.personalimdb.models.network.MovieDetails
import com.davidbronn.personalimdb.models.network.StatusModel
import com.davidbronn.personalimdb.utils.helpers.jsonify
import com.davidbronn.personalimdb.utils.misc.DispatcherProvider
import com.davidbronn.personalimdb.utils.misc.Resource
import com.davidbronn.personalimdb.utils.misc.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

/**
 * Created by Jude on 12/January/2020
 */
class MovieDetailsRepositoryImpl @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val api: MovieDetailsApi,
    private val dao: LikedMovieDao
) : MovieDetailsRepository {

    override fun insertMovie(movieId: Int) {
        dao.insertMovie(LikedMovie(movieId))
    }

    override fun deleteMovie(movieId: Int) {
        dao.deleteMovie(LikedMovie(movieId))
    }

    override fun checkIfLikedMovie(movieId: Int): LikedMovie? {
        return dao.checkLikedMovie(movieId)
    }

    override fun fetchMoviesCast(movieId: Int): Flow<Resource<List<MovieCastItem>>> {
        return flow {
            val response = api.fetchMoviesCreditAsync(movieId)
            if (response.isSuccessful) {
                val movieBody = response.body()
                val moviesCast = movieBody?.cast?.asSequence()
                    ?.filter { !it!!.profilePath.isNullOrBlank() }?.map {
                        return@map MovieCastItem(
                            url = it!!.profilePath,
                            title = it.name,
                            isMovie = false
                        )
                    }?.toList() ?: Collections.emptyList()
                emit(Resource(Status.SUCCESS, if (moviesCast.size > 9) moviesCast.take(9) else moviesCast, null))
            } else {
                val statusModel = response.errorBody()?.string()?.jsonify<StatusModel>()
                emit(Resource(Status.ERROR, null, statusModel!!.statusMessage))
            }
        }.flowOn(dispatchers.io())
    }

    override fun fetchSimilarMovies(movieId: Int): Flow<Resource<List<MovieCastItem>>> {
        return flow {
            val response = api.fetchSimilarMoviesAsync(movieId)
            if (response.isSuccessful) {
                val movieBody = response.body()
                val similarMovies = movieBody?.results?.asSequence()
                    ?.filter { !it.posterPath.isNullOrBlank() }?.map {
                        return@map MovieCastItem(
                            url = it.posterPath,
                            title = it.title,
                            isMovie = false
                        )
                    }?.toList() ?: Collections.emptyList()
                emit(Resource(Status.SUCCESS, if (similarMovies.size > 9) similarMovies.take(9) else similarMovies, null))
            } else {
                val statusModel = response.errorBody()?.string()?.jsonify<StatusModel>()
                emit(Resource(Status.ERROR, null, statusModel!!.statusMessage))
            }
        }.flowOn(dispatchers.io())
    }

    override fun fetchMovieDetails(movieId: Int): Flow<Resource<MovieDetails>> {
        return flow {
            delay(500)
            val response = api.fetchMovieDetailsAsync(movieId)
            if (response.isSuccessful) {
                val movieBody = response.body()
                emit(Resource(Status.SUCCESS, movieBody, null))
            } else {
                val statusModel = response.errorBody()?.string()?.jsonify<StatusModel>()
                emit(Resource(Status.ERROR, null, statusModel!!.statusMessage))
            }
        }.flowOn(dispatchers.io())
    }
}