package com.davidbronn.personalimdb.utils.helpers

import com.davidbronn.personalimdb.BuildConfig
import com.davidbronn.personalimdb.utils.misc.MovieConstants
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit

inline fun <reified T> apiInstance(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
        val originalHttpUrl = original.url
        val newHttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(MovieConstants.Keys.MOVIE_API_KEY, BuildConfig.MOVIE_API_KEY)
            .build()
        val requestBuilder = original.newBuilder().url(newHttpUrl)
        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }
}


