package com.davidbronn.personalimdb.di

import androidx.room.Room
import com.davidbronn.personalimdb.repository.moviedetails.MovieDetailsApi
import com.davidbronn.personalimdb.repository.moviedetails.MovieDetailsRepository
import com.davidbronn.personalimdb.repository.moviedetails.MovieDetailsRepositoryImpl
import com.davidbronn.personalimdb.repository.moviedetails.MoviesDatabase
import com.davidbronn.personalimdb.ui.moviedetails.MovieDetailsViewModel
import com.davidbronn.personalimdb.utils.helpers.apiInstance
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Created by Jude on 12/January/2020
 */

val movieDetails = module {

    single {
        Room.databaseBuilder(androidApplication(), MoviesDatabase::class.java, "movies-db")
            .allowMainThreadQueries()
            .build()
    }

    single { get<MoviesDatabase>().likedMovieDao() }

    factory { apiInstance<MovieDetailsApi>(get()) }

    factory { MovieDetailsRepositoryImpl(get(), get()) as MovieDetailsRepository}

    factory { MovieDetailsViewModel(getProperty(KoinKeys.MOVIE_ID), get()) }
}