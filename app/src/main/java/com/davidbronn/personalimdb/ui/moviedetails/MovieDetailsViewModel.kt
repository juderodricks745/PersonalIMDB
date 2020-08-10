package com.davidbronn.personalimdb.ui.moviedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.davidbronn.personalimdb.models.network.MovieCastItem
import com.davidbronn.personalimdb.models.network.MovieDetails
import com.davidbronn.personalimdb.repository.moviedetails.MovieDetailsRepository
import com.davidbronn.personalimdb.utils.misc.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * Created by Jude on 12/January/2020
 */

@ExperimentalCoroutinesApi
class MovieDetailsViewModel @ViewModelInject constructor(
    private val repository: MovieDetailsRepository
) : ViewModel() {

    val progress = MutableLiveData<Boolean>()

    var movieID = MutableLiveData<Int>().apply { value = INVALID_MOVIE_ID }
    val isMovieLiked = MutableLiveData<Boolean>().apply { value = false }

    // region [SIMILAR MOVIES]
    val moviesListLiveData = MutableLiveData<List<MovieCastItem>>()
    val showMoviesIfAvailable = moviesListLiveData.map { it.isNotEmpty() }
    // endregion

    // region [MOVIES CAST CREWS]
    val creditListLiveData = MutableLiveData<List<MovieCastItem>>()
    val showCastsIfAvailable = creditListLiveData.map { it.isNotEmpty() }
    // endregion

    // region [MOVIE DETAILS]
    val movieDetails = MutableLiveData<MovieDetails>()
    // endregion

    fun setMovieID(movieId: Int) {
        movieID.value = movieId
        fetchMovieDetails()
        fetchSimilarMovies()
        fetchCreditsAndCasts()
    }

    private fun fetchMovieDetails() {
        viewModelScope.launch {
            repository.fetchMovieDetails(movieID.value!!)
                .onStart {
                    progress.value = true
                }.onCompletion {
                    progress.value = false
                }
                .collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        movieDetails.value = it.data
                    }
                    Status.ERROR -> {

                    }
                    Status.LOADING -> {

                    }
                }
            }
        }
    }

    private fun fetchSimilarMovies() {
        viewModelScope.launch {
            repository.fetchSimilarMovies(movieID.value!!)
                .collect {
                    when(it.status) {
                        Status.SUCCESS -> {
                            moviesListLiveData.value = it.data
                        }
                        Status.ERROR -> {

                        }
                        Status.LOADING -> {

                        }
                    }
                }
        }
    }

    private fun fetchCreditsAndCasts() {
        viewModelScope.launch {
            repository.fetchMoviesCast(movieID.value!!)
                .collect {
                    when(it.status) {
                        Status.SUCCESS -> {
                            creditListLiveData.value = it.data
                        }
                        Status.ERROR -> {

                        }
                        Status.LOADING -> {

                        }
                    }
                }
        }
    }

    /**
     * Sets whether the movie is liked/not liked
     */
    fun setLikedMovie() {
        val movieID = movieID.value!!
        if (repository.checkIfLikedMovie(movieID) != null) {
            repository.deleteMovie(movieID)
        } else {
            repository.insertMovie(movieID)
        }
        isMovieLiked.value = repository.checkIfLikedMovie(movieID) != null
    }

    companion object {
        const val INVALID_MOVIE_ID: Int = -1
    }
}