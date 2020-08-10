package com.davidbronn.personalimdb.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.davidbronn.personalimdb.repository.moviedetails.MovieDetailsRepository
import com.davidbronn.personalimdb.ui.moviedetails.MovieDetailsViewModel
import com.davidbronn.personalimdb.utils.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Jude on 09/July/2020
 */
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MovieDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @MockK
    lateinit var repository: MovieDetailsRepository
    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MovieDetailsViewModel(repository)
    }

    @Test
    fun `test that when movie text is entered, then loading LiveData is called`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        /*val observer: Observer<List<MovieCastItem>> = mockk(relaxUnitFun = true)
        viewModel.creditListLiveData.observeForever(observer)

        val viewStateLiveData: MutableLiveData<Result<List<MovieCastItem>>> = MutableLiveData()
        viewStateLiveData.value = Result.Success(emptyList())


        coEvery { repository.fetchMoviesCast(1) } returns (viewStateLiveData.value as Result.Success<List<MovieCastItem>>)
        viewModel.setMovieID(1)

        val resource = Result.Success(emptyList<MovieCastItem>())
        verify { observer.onChanged(resource.data) }
        Assert.assertEquals(resource, Result.Success(emptyList<MovieCastItem>()))*/
    }
}