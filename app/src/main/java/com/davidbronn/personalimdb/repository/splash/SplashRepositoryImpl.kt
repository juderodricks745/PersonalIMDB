package com.davidbronn.personalimdb.repository.splash

import com.davidbronn.personalimdb.data.models.GenreModel
import com.davidbronn.personalimdb.data.models.StatusModel
import com.davidbronn.personalimdb.utils.Result
import com.davidbronn.personalimdb.utils.jsonify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SplashRepositoryImpl(
    private val remote: Remote,
    private val preference: Preference
) : SplashRepository {

    override suspend fun fetchGenresAsync(): Result<List<GenreModel.Genre>?>? {
        return withContext(Dispatchers.IO) {
            val response = remote.fetchGenreAsync().await()
            if (response.isSuccessful) {
                val genreBody = response.body()
                genreBody?.let {
                    preference.saveGenres(genreBody.genres)
                    return@withContext Result.Success(genreBody.genres)
                }
            } else {
                val statusModel = response.errorBody()?.string()?.jsonify<StatusModel>()
                Result.Error(RuntimeException(statusModel?.statusMessage ?: ""))
            }
        }
    }
}