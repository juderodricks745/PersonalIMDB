package com.davidbronn.personalimdb.ui.moviedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidbronn.personalimdb.models.network.MovieCastItem
import com.davidbronn.personalimdb.models.network.MovieDetails
import com.davidbronn.personalimdb.repository.moviedetails.MovieDetailsRepository
import com.davidbronn.personalimdb.utils.Result
import kotlinx.coroutines.*
import timber.log.Timber

/**
 * Created by Jude on 12/January/2020
 */
class MovieDetailsViewModel(
    private val repository: MovieDetailsRepository)
    : ViewModel() {

    private var movieId = 0
    val title = MutableLiveData<String>().apply { value = "" }
    val genres = MutableLiveData<String>().apply { value = "" }
    val runtime = MutableLiveData<String>().apply { value = "" }
    val synopsis = MutableLiveData<String>().apply { value = "" }
    val posterPath = MutableLiveData<String>().apply { value = "" }
    val releaseDate = MutableLiveData<String>().apply { value = "" }
    val backDropPath = MutableLiveData<String>().apply { value = "" }
    val isMovieLiked = MutableLiveData<Boolean>().apply { value = false }

    val moviesListLiveData = MutableLiveData<List<MovieCastItem>>()
    val creditListLiveData = MutableLiveData<List<MovieCastItem>>()

    fun fetchMovieDetails(movieId: Int) {
        this.movieId = movieId
        viewModelScope.launch(Dispatchers.Main) {
            when(val result = repository.getMovieDetails(movieId)) {
                is Result.Success -> {
                    setMovieDetails(result.data)
                }
                is Result.Error -> {
                    Timber.e(result.exception)
                }
                Result.Loading -> {

                }
            }
        }
    }

    fun fetchSimilarMovies(movieId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            when(val result = repository.fetchSimilarMovies(movieId)) {
                is Result.Success -> {
                    val movieItems = if (result.data?.size!! > 10) result.data.take(10) else result.data
                    val movies = movieItems.map { item ->
                        MovieCastItem(url = item?.posterPath, title = item?.title, isMovie = true, rating = item?.revisedVoteCount()!!)
                    }
                    moviesListLiveData.value = movies
                }
            }
        }
    }

    fun fetchCastByMovies(movieId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            when(val result = repository.fetchMoviesCast(movieId)) {
                is Result.Success -> {
                    val creditItems = if (result.data.size > 10) result.data.take(10) else result.data
                    val credits = creditItems.map { item ->
                        MovieCastItem(url = item?.profilePath, title = item?.name, isMovie = false)
                    }
                    creditListLiveData.value = credits
                }
            }
        }
    }

    fun setLikedMovie() {
        if (repository.checkIfLikedMovie(movieId) != null) {
            repository.deleteMovie(movieId, false)
        } else {
            repository.insertMovie(movieId, true)
        }
        isMovieLiked.value = repository.checkIfLikedMovie(movieId) != null
    }

    private fun setMovieDetails(movieDetail: MovieDetails) {
        title.value = movieDetail.title
        runtime.value = movieDetail.movieRuntime()
        synopsis.value = movieDetail.overview
        posterPath.value = movieDetail.posterPath
        releaseDate.value = movieDetail.releaseDate
        backDropPath.value = movieDetail.backdropPath
        isMovieLiked.value = repository.checkIfLikedMovie(movieId) != null
        genres.value = movieDetail.genres?.map { it?.name }?.joinToString(", ")
    }
}