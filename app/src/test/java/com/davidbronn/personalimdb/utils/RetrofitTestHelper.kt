package com.davidbronn.personalimdb.utils

import com.davidbronn.personalimdb.utils.helpers.RequestInterceptor
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Jude on 24/September/2020
 */

inline fun <reified T> MockWebServer.api(): T {
    return Retrofit.Builder()
        .baseUrl(this.url(""))
        .client(
            OkHttpClient.Builder()
                .addInterceptor(RequestInterceptor())
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(T::class.java)
}

fun MockWebServer.enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
    val inputStream = javaClass.classLoader!!
        .getResourceAsStream("api-response/$fileName")
    val source = inputStream.source().buffer()
    val mockResponse = MockResponse()
    for ((key, value) in headers) {
        mockResponse.addHeader(key, value)
    }
    this.enqueue(
        mockResponse.setBody(source.readString(Charsets.UTF_8))
    )
}