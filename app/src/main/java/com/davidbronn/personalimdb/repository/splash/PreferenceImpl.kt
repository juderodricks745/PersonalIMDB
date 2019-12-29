package com.davidbronn.personalimdb.repository.splash

import android.content.SharedPreferences
import com.davidbronn.personalimdb.data.models.GenreModel
import com.davidbronn.personalimdb.repository.PrefKeys
import com.davidbronn.personalimdb.utils.jsonify
import com.davidbronn.personalimdb.utils.stringify

class PreferenceImpl(private val prefs: SharedPreferences) : Preference {

    override fun getGenres(): List<GenreModel.Genre>? {
        val genre = prefs.getString(PrefKeys.GENRES, "")!!
        return genre.jsonify<List<GenreModel.Genre>>()
    }

    override fun saveGenres(genres: List<GenreModel.Genre>) {
        val genre = stringify(genres)
        prefs.edit().putString(PrefKeys.GENRES, genre).apply()
    }
}