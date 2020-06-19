package com.davidbronn.personalimdb

import android.app.Application
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AppController : Application() {

    override fun onCreate() {
        super.onCreate()

        // Debug only Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {

        var gson: Gson? = null
        fun requireGson(): Gson? {
            if (gson == null) {
                gson = Gson()
            }
            return gson
        }
    }
}