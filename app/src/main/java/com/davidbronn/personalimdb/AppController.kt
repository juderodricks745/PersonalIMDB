package com.davidbronn.personalimdb

import android.app.Application
import com.davidbronn.personalimdb.di.*
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class AppController : Application() {

    override fun onCreate() {
        super.onCreate()

        // Debug only Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Koin Initializations
        startKoin {
            androidContext(this@AppController)
            modules(
                listOf(
                    base,
                    movieList,
                    movieDetails,
                    searchMovies
                )
            )
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