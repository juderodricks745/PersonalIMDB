package com.davidbronn.personalimdb.di

import com.davidbronn.personalimdb.repository.searchmovies.SearchMoviesApi
import com.davidbronn.personalimdb.repository.searchmovies.SearchMoviesRepository
import com.davidbronn.personalimdb.repository.searchmovies.SearchMoviesRepositoryImpl
import com.davidbronn.personalimdb.ui.search.SearchMoviesViewModel
import com.davidbronn.personalimdb.utils.apiInstance
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Jude on 13/January/2020
 */

val searchMovies = module {

    // Search Movies API
    single { apiInstance<SearchMoviesApi>(get()) }

    // DataSources
    single { SearchMoviesRepositoryImpl(get()) as SearchMoviesRepository }

    // SearchMovies ViewModel
    viewModel { SearchMoviesViewModel(get()) }
}