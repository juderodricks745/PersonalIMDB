package com.davidbronn.personalimdb.di

import com.davidbronn.personalimdb.repository.movieslist.MoviesListApi
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

    single { apiInstance<MoviesListApi>(get()) }

    factory { MoviesDataSource(getProperty(KoinKeys.MOVIE_TYPE), get()) }

    factory { MoviesDataSourceFactory(get()) }

    viewModel { MoviesListViewModel(get()) }
}