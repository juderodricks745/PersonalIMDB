package com.davidbronn.personalimdb.di

import com.davidbronn.personalimdb.repository.moviedetails.LikedMovieDao
import com.davidbronn.personalimdb.repository.moviedetails.MovieDetailsApi
import com.davidbronn.personalimdb.repository.moviedetails.MovieDetailsRepository
import com.davidbronn.personalimdb.repository.moviedetails.MovieDetailsRepositoryImpl
import com.davidbronn.personalimdb.repository.searchmovies.SearchMoviesApi
import com.davidbronn.personalimdb.repository.searchmovies.SearchMoviesRepository
import com.davidbronn.personalimdb.repository.searchmovies.SearchMoviesRepositoryImpl
import com.davidbronn.personalimdb.utils.misc.DefaultDispatcherProvider
import com.davidbronn.personalimdb.utils.misc.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 * Created by Jude on 28/June/2020
 */
@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun provideDispatchers(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    fun provideMovieDetailsRepository(dispatchers: DispatcherProvider, api: MovieDetailsApi, dao: LikedMovieDao): MovieDetailsRepository =
        MovieDetailsRepositoryImpl(dispatchers, api, dao)

    @Provides
    fun provideSearchMoviesRepository(dispatchers: DispatcherProvider, api: SearchMoviesApi): SearchMoviesRepository =
        SearchMoviesRepositoryImpl(dispatchers, api)
}