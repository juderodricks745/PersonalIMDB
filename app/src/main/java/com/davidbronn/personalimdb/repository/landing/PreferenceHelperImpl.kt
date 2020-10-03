package com.davidbronn.personalimdb.repository.landing

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Jude on 17/May/2020
 */
@Singleton
class PreferenceHelperImpl @Inject constructor(@ApplicationContext context: Context) : PreferenceHelper {

    private val prefs = context.getSharedPreferences("default", Context.MODE_PRIVATE)

    override fun getTheme(): String? = prefs.getString(IS_DARK, UNDEFINED_DARK_MODE)

    override fun setTheme(value: String) = prefs.edit().putString(IS_DARK, value).apply()

    companion object {
        const val IS_DARK = "is_dark"
        const val IS_DARK_MODE = "is_dark_mode"
        const val NOT_DARK_MODE = "not_dark_mode"
        const val UNDEFINED_DARK_MODE = "undefined_dark_mode"
    }
}