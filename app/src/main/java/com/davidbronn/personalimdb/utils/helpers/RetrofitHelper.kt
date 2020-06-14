package com.davidbronn.personalimdb.utils.helpers

import com.davidbronn.personalimdb.BuildConfig
import com.davidbronn.personalimdb.utils.misc.MovieConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * If only app is Debug type, we log Headers & Body, else no logging
 * is done.
 */
fun interceptor(): Interceptor {
    return HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }
}

fun okhttp(interceptor: Interceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(RequestInterceptor())
        .build()
}

fun retrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

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


