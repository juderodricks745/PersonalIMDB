package com.davidbronn.personalimdb.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.davidbronn.personalimdb.repository.movies.MoviesApi
import com.davidbronn.personalimdb.utils.api
import com.davidbronn.personalimdb.utils.enqueueResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Jude on 29/September/2020
 */
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MoviesApiTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: MoviesApi
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
    fun `fetch popular movies list for second page`() {
        runBlocking {
            mockWebServer.enqueueResponse("popular-movies.json")
            val response = service.fetchPopularMoviesAsync(2).body()
            Assert.assertNotNull(response)
            Assert.assertEquals(response?.results?.size, 20)
            Assert.assertEquals(response?.results?.get(0)?.title, "Infamous")
        }
    }
}