package com.davidbronn.personalimdb.utils.misc

/**
 * Created by Jude on 29/March/2020
 */
interface MovieConstants {

    object Keys {
        const val MOVIE_API_KEY = "api_key" // Key for request from API
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
}