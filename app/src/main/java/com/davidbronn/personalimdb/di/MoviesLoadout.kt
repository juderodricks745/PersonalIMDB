package com.davidbronn.personalimdb.di

import com.davidbronn.personalimdb.repository.movieslist.MoviesApi
import com.davidbronn.personalimdb.ui.movieslist.MoviesDataSource
import com.davidbronn.personalimdb.ui.movieslist.MoviesDataSourceFactory
import com.davidbronn.personalimdb.ui.movieslist.MoviesListViewModel
import com.davidbronn.personalimdb.utils.helpers.apiInstance
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Jude on 04/January/2020
 */
val movieList = module {

    single { apiInstance<MoviesApi>(get()) }

    factory { MoviesDataSource(getProperty(KoinKeys.MOVIE_TYPE), get()) }
    factory { MoviesDataSourceFactory(get()) }

    viewModel { MoviesListViewModel(get()) }
}