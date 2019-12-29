package com.davidbronn.personalimdb.di

import com.davidbronn.personalimdb.BuildConfig
import com.davidbronn.personalimdb.utils.initPrefs
import com.davidbronn.personalimdb.utils.interceptor
import com.davidbronn.personalimdb.utils.okhttp
import com.davidbronn.personalimdb.utils.retrofit
import org.koin.dsl.module

val base = module {
    // SharedPreferences Initializations
    single { initPrefs(BuildConfig.PREF_NAME, get()) }

    // Retrofit Initializations
    single { interceptor() }
    single { okhttp(get()) }
    single { retrofit(BuildConfig.BASE_URL, get()) }
}