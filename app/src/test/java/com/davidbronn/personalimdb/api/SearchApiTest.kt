package com.davidbronn.personalimdb.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.davidbronn.personalimdb.repository.search.SearchMoviesApi
import com.davidbronn.personalimdb.utils.api
import com.davidbronn.personalimdb.utils.enqueueResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Jude on 28/June/2020
 */
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SearchApiTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: SearchMoviesApi
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = mockWebServer.api()
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun `fetch movies for given search text`() {
        runBlocking {
            val movieText = "Avenger"
            mockWebServer.enqueueResponse("search-movies.json")
            val response = service.fetchMoviesByTextAsync(movieText, 1).body()
            Assert.assertNotNull(response)
            Assert.assertEquals(
                response?.results?.get(1)?.title,
                "Captain America: The First Avenger"
            )
        }
    }
}