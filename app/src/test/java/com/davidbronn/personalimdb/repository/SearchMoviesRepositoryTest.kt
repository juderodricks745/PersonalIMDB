package com.davidbronn.personalimdb.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.davidbronn.personalimdb.repository.searchmovies.SearchMoviesRepository
import com.davidbronn.personalimdb.utils.CoroutineTestRule
import com.davidbronn.personalimdb.utils.misc.Result
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Jude on 26/June/2020
 * For using Mockk library visit
 * https://medium.com/@marco_cattaneo/how-use-and-test-kotlin-coroutines-with-mockk-library-49ddb2c9ee5f
 */
@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class SearchMoviesRepositoryTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @MockK
    val repository = mockk<SearchMoviesRepository>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `fetch movies with search text & check for error response`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // Use appropriate methods from proper dependencies to mock requests & responses
        // coEvery supports suspend functions
        coEvery { repository.fetchMovies("Avengers") } returns Result.Error(RuntimeException())
        val response = repository.fetchMovies("Avengers")
        Assert.assertNotNull(response)
    }
}