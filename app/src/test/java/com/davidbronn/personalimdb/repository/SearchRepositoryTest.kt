package com.davidbronn.personalimdb.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.search.SearchRepositoryImpl
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
 * Created by Jude on 03/October/2020
 */
@ExperimentalCoroutinesApi
class SearchRepositoryTest {

    // region [DECLARATION]
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var testRule = CoroutineTestRule()
    // endregion

    @MockK
    private var repository = mockk<SearchRepositoryImpl>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `search Movies`() {
        testRule.testDispatcher.runBlockingTest {
            val searchText = "Avenger"
            val searchMoviesFlow =
                flow<PagingData<ResultsItem>> { PagingData.from(Collections.emptyList()) }
            every { repository.searchMovies(searchText) } returns searchMoviesFlow
            val searchResponse = repository.searchMovies(searchText)
            Assert.assertEquals(searchMoviesFlow, searchResponse)
        }
    }


}