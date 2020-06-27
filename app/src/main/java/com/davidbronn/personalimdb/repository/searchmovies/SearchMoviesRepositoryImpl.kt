package com.davidbronn.personalimdb.repository.searchmovies

import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.models.network.StatusModel
import com.davidbronn.personalimdb.utils.helpers.jsonify
import com.davidbronn.personalimdb.utils.misc.DispatcherProvider
import com.davidbronn.personalimdb.utils.misc.Result
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Jude on 13/January/2020
 */
class SearchMoviesRepositoryImpl @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val api: SearchMoviesApi
) : SearchMoviesRepository {

    override suspend fun fetchMovies(
        searchText: String
    ): Result<List<ResultsItem?>?>? {
        return withContext(dispatchers.io()) {
            val response = api.fetchMoviesByTextAsync(searchText)
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