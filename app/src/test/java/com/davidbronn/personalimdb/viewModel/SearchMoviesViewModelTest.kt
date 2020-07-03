package com.davidbronn.personalimdb.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.searchmovies.SearchMoviesRepository
import com.davidbronn.personalimdb.ui.search.SearchMoviesViewModel
import com.davidbronn.personalimdb.utils.CoroutineTestRule
import com.davidbronn.personalimdb.utils.misc.Resource
import com.davidbronn.personalimdb.utils.misc.Status
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Better example for ViewModel testing;
 * https://github.com/furkanaskin/Weatherapp
 *
 * // Given -> When -> Then
 * Above can be read as with these GIVEN values, WHEN operation is to be performed,
 * THEN give these as result.
 *
 * Created by Jude on 02/July/2020
 */
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SearchMoviesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @MockK
    lateinit var repository: SearchMoviesRepository
    private lateinit var viewModel: SearchMoviesViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = SearchMoviesViewModel(repository)
    }

    @Test
    fun `test that when movie text is entered, then loading LiveData is called`() {
        // Given
        val observer: Observer<Resource<List<ResultsItem>>> = mockk(relaxUnitFun = true)
        viewModel.getResource().observeForever(observer)

        val viewStateLiveData: MutableLiveData<Resource<List<ResultsItem>>> = MutableLiveData()
        viewStateLiveData.value = Resource(Status.LOADING, emptyList(), "")

        // When
        every { repository.fetchMoviesByLiveData("Avengers") } returns viewStateLiveData
        viewModel.setMovieSearchParams("Avengers")

        // Then
        val resource = Resource(Status.LOADING, emptyList<ResultsItem>(), "")
        verify { observer.onChanged(resource) }
        Assert.assertEquals(resource.status, Status.LOADING)
    }
}