package com.davidbronn.personalimdb.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.davidbronn.personalimdb.BuildConfig
import com.davidbronn.personalimdb.repository.searchmovies.SearchMoviesApi
import com.davidbronn.personalimdb.utils.helpers.RequestInterceptor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Jude on 28/June/2020
 */
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SearchMoviesApiTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: SearchMoviesApi
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .client(OkHttpClient.Builder()
                .addInterceptor(RequestInterceptor())
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SearchMoviesApi::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun `search api result produces non null response`() {
        runBlocking {
            val movieText = "Avenger"
            enqueueResponse("search-movies.json")
            val response = service.fetchMoviesByTextAsync(movieText).body()
            val request = mockWebServer.takeRequest()
            Assert.assertNotNull(response)
            Assert.assertEquals(request.path, "/3/search/movie?query=$movieText&api_key=${BuildConfig.MOVIE_API_KEY}")
        }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse.setBody(source.readString(Charsets.UTF_8))
        )
    }
}