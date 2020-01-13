package com.davidbronn.personalimdb.di

import com.davidbronn.personalimdb.repository.movieslist.*
import com.davidbronn.personalimdb.repository.splash.SplashRepository
import com.davidbronn.personalimdb.repository.splash.SplashRepositoryImpl
import com.davidbronn.personalimdb.ui.movieslist.MoviesListViewModel
import com.davidbronn.personalimdb.utils.apiInstance
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Jude on 04/January/2020
 */
val movieList = module {

    // Splash API Initializations
    single { apiInstance<MoviesApi>(get()) }

    // ViewModel Initializations
    // viewModel { MoviesListViewModel(get(), "T") }
}