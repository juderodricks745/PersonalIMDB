package com.davidbronn.personalimdb.di

import com.davidbronn.personalimdb.BuildConfig
import com.davidbronn.personalimdb.utils.helpers.interceptor
import com.davidbronn.personalimdb.utils.helpers.okhttp
import com.davidbronn.personalimdb.utils.helpers.retrofit
import org.koin.dsl.module

val base = module {

    single { interceptor() }

    single { okhttp(get()) }

    single { retrofit(BuildConfig.BASE_URL, get()) }
}