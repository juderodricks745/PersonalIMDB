package com.davidbronn.personalimdb.ui.moviedetails

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidbronn.personalimdb.models.network.CastItem
import com.davidbronn.personalimdb.models.network.MovieCastItem
import com.davidbronn.personalimdb.models.network.MovieDetails
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.moviedetails.MovieDetailsRepository
import com.davidbronn.personalimdb.ui.moviedetails.MovieDetailsViewModel.Keys.MOVIE_ID
import com.davidbronn.personalimdb.utils.misc.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by Jude on 12/January/2020
 */
class MovieDetailsViewModel @ViewModelInject constructor(
    private val repository: MovieDetailsRepository,
    @Assisted private val handle: SavedStateHandle
) : ViewModel() {

    private val movieID = handle.get<Int>(MOVIE_ID)!!

    val title = MutableLiveData<String>().apply { value = "" }
    val genres = MutableLiveData<String>().apply { value = "" }
    val runtime = MutableLiveData<String>().apply { value = "" }
    val synopsis = MutableLiveData<String>().apply { value = "" }
    val posterPath = MutableLiveData<String>().apply { value = "" }
    val tagLine = MutableLiveData<String>().apply { value = "" }
    val showTagLine = MutableLiveData<Boolean>().apply { value = true }
    val releaseDate = MutableLiveData<String>().apply { value = "" }
    val backDropPath = MutableLiveData<String>().apply { value = "" }
    val isMovieLiked = MutableLiveData<Boolean>().apply { value = false }

    val progress = MutableLiveData<Boolean>()
    val moviesListLiveData = MutableLiveData<List<MovieCastItem>>()
    val creditListLiveData = MutableLiveData<List<MovieCastItem>>()

    val showCastsIfAvailable = MutableLiveData<Boolean>().apply { value = false }
    val showMoviesIfAvailable = MutableLiveData<Boolean>().apply { value = false }

    init {
        fetchMovieAndRelatedDetails()
    }

    /**
     * Sets whether the movie is liked/not liked
     */
    fun setLikedMovie() {
        if (repository.checkIfLikedMovie(movieID) != null) {
            repository.deleteMovie(movieID, false)
        } else {
            repository.insertMovie(movieID, true)
        }
        isMovieLiked.value = repository.checkIfLikedMovie(movieID) != null
    }

    /**
     * Fetches Movie Details for the respective Movie ID, also fetches the similar
     * kind of movies & the cast associated with the movie
     */
    private fun fetchMovieAndRelatedDetails() {
        viewModelScope.launch {
            val movieDetails = async { repository.getMovieDetails(movieID) }.await()
            val similarMovies = async { repository.fetchSimilarMovies(movieID) }.await()
            val movieCast = async { repository.fetchMoviesCast(movieID) }.await()
            movieDetails?.let { handleMovieDetail(it) }
            similarMovies?.let { handleSimilarMovies(it) }
            movieCast?.let { handleMoviesCast(it) }
        }
    }

    private fun handleMovieDetail(response: Result<MovieDetails>) {
        when (response) {
            is Result.Success -> {
                title.value = response.data.title
                synopsis.value = response.data.overview
                posterPath.value = response.data.posterPath
                releaseDate.value = response.data.releaseDate
                backDropPath.value = response.data.backdropPath
                isMovieLiked.value = repository.checkIfLikedMovie(movieID) != null
                runtime.value = response.data.movieRuntime()
                if (!response.data.tagline.isNullOrBlank()) {
                    showTagLine.value = true
                    tagLine.value = "\"${response.data.tagline}\""
                } else {
                    showTagLine.value = false
                }
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
                response.data?.let { similar ->
                    if (!similar.isNullOrEmpty()) {
                        showMoviesIfAvailable.value = true
                        val movieItems =
                            if (similar.size > 9) similar.take(9) else similar
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
                response.data?.let { castlist ->
                    if (!castlist.isNullOrEmpty()) {
                        val creditItems =
                            if (castlist.size > 9) castlist.take(9) else castlist
                        val credits = creditItems
                            .filter { item ->
                                !item?.profilePath.isNullOrBlank()
                            }
                            .map { item ->
                                MovieCastItem(
                                    url = item?.profilePath,
                                    title = item?.name,
                                    isMovie = false
                                )
                            }
                        if (credits.isNotEmpty()) {
                            showCastsIfAvailable.value = true
                            creditListLiveData.value = credits
                        }
                    }
                }
            }
            is Result.Error -> {
                Timber.e(response.exception)
            }
        }
    }

    object Keys {
        const val MOVIE_ID = "movie_id"
    }
}