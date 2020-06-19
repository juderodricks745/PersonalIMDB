package com.davidbronn.personalimdb.repository.landing

/**
 * Created by Jude on 17/June/2020
 */
interface PreferenceHelper {

    fun getTheme(): String?
    fun setTheme(value: String)
}