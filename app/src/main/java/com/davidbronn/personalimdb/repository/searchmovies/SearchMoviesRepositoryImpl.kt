package com.davidbronn.personalimdb.repository.searchmovies

import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.models.network.StatusModel
import com.davidbronn.personalimdb.utils.helpers.jsonify
import com.davidbronn.personalimdb.utils.misc.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Jude on 13/January/2020
 */
class SearchMoviesRepositoryImpl(private val api: SearchMoviesApi) : SearchMoviesRepository {

    override suspend fun fetchMovies(
        searchText: String,
        includeAdult: String
    ): Result<List<ResultsItem?>?>? {
        return withContext(Dispatchers.IO) {
            val response = api.fetchMoviesByTextAsync(searchText, includeAdult).await()
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
}