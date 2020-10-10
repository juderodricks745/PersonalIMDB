package com.davidbronn.personalimdb.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.davidbronn.personalimdb.models.network.MovieCastItem
import com.davidbronn.personalimdb.models.network.MovieDetails
import com.davidbronn.personalimdb.repository.details.DetailsRepository
import com.davidbronn.personalimdb.utils.CoroutineTestRule
import com.davidbronn.personalimdb.utils.TestUtil
import com.davidbronn.personalimdb.utils.misc.Resource
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

/**
 * Created by Jude on 03/October/2020
 */
@ExperimentalCoroutinesApi
class DetailsRepositoryTest {

    // region [DECLARATION]
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var testRule = CoroutineTestRule()
    // endregion

    @MockK
    private var repository = mockk<DetailsRepository>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `fetch movies details`() {
        testRule.testDispatcher.runBlockingTest {
            val detailsFlow =
                flow<Resource<MovieDetails>> { Resource.success(TestUtil.createMoviesDetail()) }
            every { repository.fetchMovieDetails(TestUtil.MOVIE_ID) } returns detailsFlow
            val detailsResponse = repository.fetchMovieDetails(TestUtil.MOVIE_ID)
            Assert.assertEquals(detailsFlow, detailsResponse)
        }
    }

    @Test
    fun `fetch movies similar to a movie`() {
        testRule.testDispatcher.runBlockingTest {
            val similarMoviesFlow =
                flow<Resource<List<MovieCastItem>>> { Resource.success(TestUtil.createSimilarMovies()) }
            every { repository.fetchSimilarMovies(TestUtil.MOVIE_ID) } returns similarMoviesFlow
            val similarMoviesResponse = repository.fetchSimilarMovies(TestUtil.MOVIE_ID)
            Assert.assertEquals(similarMoviesFlow, similarMoviesResponse)
        }
    }

    @Test
    fun `fetch cast details for a movie`() {
        testRule.testDispatcher.runBlockingTest {
            val moviesCastFlow =
                flow<Resource<List<MovieCastItem>>> { Resource.success(TestUtil.createMoviesCast()) }
            every { repository.fetchMoviesCast(TestUtil.MOVIE_ID) } returns moviesCastFlow
            val moviesCastResponse = repository.fetchMoviesCast(TestUtil.MOVIE_ID)
            Assert.assertEquals(moviesCastFlow, moviesCastResponse)
        }
    }
}