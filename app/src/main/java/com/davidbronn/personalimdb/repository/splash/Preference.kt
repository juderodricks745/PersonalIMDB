package com.davidbronn.personalimdb.repository.splash

import com.davidbronn.personalimdb.data.models.GenreModel

interface Preference {

    fun getGenres(): List<GenreModel.Genre>?
    fun saveGenres(genres: List<GenreModel.Genre>)
}