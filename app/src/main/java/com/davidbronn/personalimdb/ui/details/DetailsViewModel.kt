package com.davidbronn.personalimdb.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidbronn.personalimdb.models.network.MovieCastItem
import com.davidbronn.personalimdb.models.network.MovieDetails
import com.davidbronn.personalimdb.repository.details.DetailsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class DetailsViewModel @ViewModelInject constructor(
    private val repository: DetailsRepository
) : ViewModel() {

    var movieID = MutableLiveData<Int>().apply { value = INVALID_MOVIE_ID }
    val isMovieLiked = MutableLiveData<Boolean>().apply { value = false }

    val movieDetails = MutableLiveData<MovieDetails>()
    val moviesListLiveData = MutableLiveData<List<MovieCastItem>>()
    val creditListLiveData = MutableLiveData<List<MovieCastItem>>()

    fun setMovieID(movieId: Int) {
        movieID.value = movieId
        fetchMovieDetails()
        fetchSimilarMovies()
        fetchCreditsAndCasts()
    }

    private fun fetchMovieDetails() {
        viewModelScope.launch {
            repository.fetchMovieDetails(movieID.value!!)
                .collect {
                    movieDetails.value = it.data
                }
        }
    }

    private fun fetchSimilarMovies() {
        viewModelScope.launch {
            repository.fetchSimilarMovies(movieID.value!!)
                .collect {
                    moviesListLiveData.value = it.data
                }
        }
    }

    private fun fetchCreditsAndCasts() {
        viewModelScope.launch {
            repository.fetchMoviesCast(movieID.value!!)
                .collect {
                    creditListLiveData.value = it.data
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