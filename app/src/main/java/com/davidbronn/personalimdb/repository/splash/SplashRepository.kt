package com.davidbronn.personalimdb.repository.splash

import com.davidbronn.personalimdb.data.models.GenreModel
import com.davidbronn.personalimdb.utils.Result

interface SplashRepository {

    suspend fun fetchGenresAsync(): Result<List<GenreModel.Genre>?>?
}