package com.davidbronn.personalimdb.repository.landing

import android.content.SharedPreferences

/**
 * Created by Jude on 17/May/2020
 */
class PreferenceHelper(private val prefs: SharedPreferences) {

    fun getTheme(): String? = prefs.getString(IS_DARK, UNDEFINED_DARK_MODE)

    fun setTheme(value: String) = prefs.edit().putString(IS_DARK, value).apply()

    companion object {
        const val IS_DARK = "is_dark"

        const val IS_DARK_MODE = "is_dark_mode"
        const val NOT_DARK_MODE = "not_dark_mode"
        const val UNDEFINED_DARK_MODE = "undefined_dark_mode"
    }
}