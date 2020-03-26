package com.davidbronn.personalimdb.ui.moviedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidbronn.personalimdb.models.network.CastItem
import com.davidbronn.personalimdb.models.network.MovieCastItem
import com.davidbronn.personalimdb.models.network.MovieDetails
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.moviedetails.MovieDetailsRepository
import com.davidbronn.personalimdb.utils.misc.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by Jude on 12/January/2020
 */
class MovieDetailsViewModel(
    private val repository: MovieDetailsRepository
) : ViewModel() {

    private var movieId = 0
    val title = MutableLiveData<String>().apply { value = "" }
    val genres = MutableLiveData<String>().apply { value = "" }
    val runtime = MutableLiveData<String>().apply { value = "" }
    val synopsis = MutableLiveData<String>().apply { value = "" }
    val posterPath = MutableLiveData<String>().apply { value = "" }
    val releaseDate = MutableLiveData<String>().apply { value = "" }
    val backDropPath = MutableLiveData<String>().apply { value = "" }
    val isMovieLiked = MutableLiveData<Boolean>().apply { value = false }

    val progress = MutableLiveData<Boolean>()
    val moviesListLiveData = MutableLiveData<List<MovieCastItem>>()
    val creditListLiveData = MutableLiveData<List<MovieCastItem>>()

    val showCastsIfAvailable = MutableLiveData<Boolean>().apply { value = false }
    val showMoviesIfAvailable = MutableLiveData<Boolean>().apply { value = false }

    /**
     * Sets whether the movie is liked/not liked
     */
    fun setLikedMovie() {
        if (repository.checkIfLikedMovie(movieId) != null) {
            repository.deleteMovie(movieId, false)
        } else {
            repository.insertMovie(movieId, true)
        }
        isMovieLiked.value = repository.checkIfLikedMovie(movieId) != null
    }

    /**
     * Fetches Movie Details for the respective Movie ID, also fetches the similar
     * kind of movies & the cast associated with the movie
     */
    fun fetchMovieAndRelatedDetails(movieItem: ResultsItem) {
        movieId = movieItem.id!!
        handleMovieDetailsFromList(movieItem)
        viewModelScope.launch {
            //progress.value = true
            val movieDetails = async { repository.getMovieDetails(movieId) }.await()
            val similarMovies = async { repository.fetchSimilarMovies(movieId) }.await()
            val movieCast = async { repository.fetchMoviesCast(movieId) }.await()
            //progress.value = false
            movieDetails?.let { handleMovieDetail(it) }
            similarMovies?.let { handleSimilarMovies(it) }
            movieCast?.let { handleMoviesCast(it) }
        }
    }

    private fun handleMovieDetailsFromList(movieItem: ResultsItem) {
        title.value = movieItem.title
        synopsis.value = movieItem.overview
        releaseDate.value = movieItem.releaseDate
        backDropPath.value = movieItem.backdropPath
        isMovieLiked.value = repository.checkIfLikedMovie(movieId) != null
        posterPath.value = movieItem.posterPath
    }

    private fun handleMovieDetail(response: Result<MovieDetails>) {
        when (response) {
            is Result.Success -> {
                runtime.value = response.data.movieRuntime()
                releaseDate.value = response.data.releaseDate
                genres.value = response.data.genres?.map { it?.name }?.joinToString(", ")
            }
            is Result.Error -> {
                Timber.e(response.exception)
            }
        }
    }

    private fun handleSimilarMovies(response: Result<List<ResultsItem?>?>) {
        when (response) {
            is Result.Success -> {
                response.data?.let {
                    if (!it.isNullOrEmpty()) {
                        showMoviesIfAvailable.value = true
                        val movieItems =
                            if (it.size > 10) it.take(10) else it
                        val movies = movieItems.map { item ->
                            MovieCastItem(
                                url = item?.posterPath,
                                title = item?.title,
                                isMovie = true,
                                rating = item?.revisedVoteCount()!!
                            )
                        }
                        moviesListLiveData.value = movies
                    }
                }
            }
            is Result.Error -> {
                Timber.e(response.exception)
            }
        }
    }

    private fun handleMoviesCast(response: Result<List<CastItem?>?>) {
        when (response) {
            is Result.Success -> {
                response.data?.let {
                    if (!it.isNullOrEmpty()) {
                        showCastsIfAvailable.value = true
                        val creditItems =
                            if (it.size > 10) it.take(10) else it
                        val credits = creditItems.map { item ->
                            MovieCastItem(
                                url = item?.profilePath,
                                title = item?.name,
                                isMovie = false
                            )
                        }
                        creditListLiveData.value = credits
                    }
                }
            }
            is Result.Error -> {
                Timber.e(response.exception)
            }
        }
    }
}