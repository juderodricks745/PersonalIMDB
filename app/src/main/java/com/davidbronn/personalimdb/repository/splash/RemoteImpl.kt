package com.davidbronn.personalimdb.repository.splash

import com.davidbronn.personalimdb.data.models.GenreModel
import kotlinx.coroutines.Deferred
import retrofit2.Response

class RemoteImpl(private val api: SplashApi) : Remote {

    override fun fetchGenreAsync(): Deferred<Response<GenreModel>> {
        return api.fetchGenreAsync()
    }
}