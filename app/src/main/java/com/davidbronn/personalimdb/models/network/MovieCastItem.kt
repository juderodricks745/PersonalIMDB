package com.davidbronn.personalimdb.models.network

/**
 * Created by Jude on 13/January/2020
 */
data class MovieCastItem(
    var url: String? = null,
    var title: String? = null,
    var movieId: String? = null,
    var isMovie: Boolean = false,
    var rating: String = ""
)