package com.davidbronn.personalimdb.utils

import com.davidbronn.personalimdb.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * If only app is Debug type, we log Headers & Body, else no logging
 * is done.
 */
fun interceptor(): Interceptor {
    return HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            level = HttpLoggingInterceptor.Level.BODY
            level = HttpLoggingInterceptor.Level.HEADERS
        } else {
            level = HttpLoggingInterceptor.Level.NONE
        }
    }
}

fun okhttp(interceptor: Interceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
}

fun retrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

inline fun <reified T> apiInstance(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}


