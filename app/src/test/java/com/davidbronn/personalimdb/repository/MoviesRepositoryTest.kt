package com.davidbronn.personalimdb.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.movies.MoviesRepository
import com.davidbronn.personalimdb.utils.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

/**
 * Created by Jude on 02/October/2020
 */
@ExperimentalCoroutinesApi
class MoviesRepositoryTest {

    // region [DECLARATION]
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var testRule = CoroutineTestRule()
    // endregion

    @MockK
    private var repository = mockk<MoviesRepository>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test Movies`() {
        testRule.testDispatcher.runBlockingTest {
            val moviesFlow =
                flow<PagingData<ResultsItem>> { PagingData.from(Collections.emptyList()) }
            every { repository.fetchMovies() } returns moviesFlow
            val moviesResponse = repository.fetchMovies()
            Assert.assertEquals(moviesFlow, moviesResponse)
        }
    }
}