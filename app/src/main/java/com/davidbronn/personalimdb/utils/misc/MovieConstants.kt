package com.davidbronn.personalimdb.utils.misc

/**
 * Created by Jude on 29/March/2020
 */
interface MovieConstants {

    object Keys {
        const val MOVIE_APIKEY = "apiKey" // Key to read from Properties
        const val MOVIE_API_KEY = "api_key" // Key for request from API
        const val MOVIE_PROPERTIES = "movie.properties"
    }

    object Urls {
        const val POSTER_200 = "https://image.tmdb.org/t/p/w200"
        const val POSTER_500 = "https://image.tmdb.org/t/p/w500"
    }

    object Titles {
        val MOVIE_TITLES = mutableListOf(
            "Now Playing",
            "Popular Movies",
            "Top Rated Movies",
            "Upcoming Movies"
        )
    }

    object MovieList {
        const val KEY_MOVIE_PAGE = "page"
        const val MOVIE_LATEST = "movie/latest"
        const val MOVIE_NOW_PLAYING = "movie/now_playing"
        const val MOVIE_POPULAR = "movie/popular"
        const val MOVIE_TOP_RATED = "movie/top_rated"
        const val MOVIE_UPCOMING = "movie/upcoming"
    }

    object MovieDetails {
        const val KEY_MOVIE_ID = "movie_id" // Key for fetching Movie by ID
        const val MOVIE_DETAILS = "/3/movie/{movie_id}"
        const val MOVIE_SIMILAR = "/3/movie/{movie_id}/similar"
        const val MOVIE_CREDITS = "/3/movie/{movie_id}/credits"
    }

    object MovieSearch {
        const val KEY_MOVIE_QUERY = "query"
        const val MOVIE_SEARCH = "/3/search/movie"
    }
}