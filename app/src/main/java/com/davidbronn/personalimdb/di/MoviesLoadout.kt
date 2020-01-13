package com.davidbronn.personalimdb.di

import com.davidbronn.personalimdb.repository.movieslist.MoviesApi
import com.davidbronn.personalimdb.utils.apiInstance
import org.koin.dsl.module

/**
 * Created by Jude on 04/January/2020
 */
val movieList = module {

    // Movies List API
    single { apiInstance<MoviesApi>(get()) }
}