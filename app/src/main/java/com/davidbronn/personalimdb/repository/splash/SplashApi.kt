package com.davidbronn.personalimdb.repository.splash

import com.davidbronn.personalimdb.BuildConfig
import com.davidbronn.personalimdb.data.models.GenreModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface SplashApi {

    @GET("genre/movie/list?api_key=${BuildConfig.API_KEY}")
    fun fetchGenreAsync(): Deferred<Response<GenreModel>>
}