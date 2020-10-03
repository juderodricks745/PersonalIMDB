package com.davidbronn.personalimdb.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.davidbronn.personalimdb.repository.details.MovieDetailsApi
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
class DetailsApiTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: MovieDetailsApi
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
    fun `fetch movie details for particular movie`() {
        runBlocking {
            mockWebServer.enqueueResponse("details-movies.json")
            val response = service.fetchMovieDetailsAsync(MOVIE_ID).body()
            Assert.assertNotNull(response)
            Assert.assertEquals(response?.title, "Angel Has Fallen")
        }
    }

    @Test
    fun `fetch credits for particular movie`() {
        runBlocking {
            mockWebServer.enqueueResponse("credits-movies.json")
            val response = service.fetchMoviesCreditAsync(MOVIE_ID).body()
            Assert.assertNotNull(response)
            Assert.assertEquals(response?.cast?.get(0)?.name, "Gerard Butler")
            Assert.assertEquals(response?.cast?.get(0)?.character, "Mike Banning")
        }
    }

    @Test
    fun `fetch movies similar to particular movie`() {
        runBlocking {
            mockWebServer.enqueueResponse("similar-movies.json")
            val response = service.fetchSimilarMoviesAsync(MOVIE_ID).body()
            Assert.assertNotNull(response)
            Assert.assertEquals(response?.results?.get(0)?.title, "In the Line of Fire")
        }
    }

    companion object {
        const val MOVIE_ID = 423204
    }
}