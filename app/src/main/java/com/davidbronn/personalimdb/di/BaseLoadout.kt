package com.davidbronn.personalimdb.di

import android.app.Application
import android.content.SharedPreferences
import com.davidbronn.personalimdb.BuildConfig
import com.davidbronn.personalimdb.repository.landing.PreferenceHelper
import com.davidbronn.personalimdb.utils.helpers.interceptor
import com.davidbronn.personalimdb.utils.helpers.okhttp
import com.davidbronn.personalimdb.utils.helpers.retrofit
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val base = module {

    single { interceptor() }

    single { okhttp(get()) }

    single { retrofit(BuildConfig.BASE_URL, get()) }

    single {
        getSharedPrefs(androidApplication())
    }

    single {
        PreferenceHelper(get())
    }
}

fun getSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences("default", android.content.Context.MODE_PRIVATE)
}