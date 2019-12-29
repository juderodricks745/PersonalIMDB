package com.davidbronn.personalimdb.repository.splash

import com.davidbronn.personalimdb.data.models.GenreModel
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface Remote {

    fun fetchGenreAsync(): Deferred<Response<GenreModel>>
}