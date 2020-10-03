package com.davidbronn.personalimdb.di

import com.davidbronn.personalimdb.repository.details.DetailsRepository
import com.davidbronn.personalimdb.repository.details.DetailsRepositoryImpl
import com.davidbronn.personalimdb.repository.details.LikedMovieDao
import com.davidbronn.personalimdb.repository.details.MovieDetailsApi
import com.davidbronn.personalimdb.repository.movies.MoviesApi
import com.davidbronn.personalimdb.repository.movies.MoviesRepository
import com.davidbronn.personalimdb.repository.movies.MoviesRepositoryImpl
import com.davidbronn.personalimdb.repository.person.PersonApi
import com.davidbronn.personalimdb.repository.person.PersonRepository
import com.davidbronn.personalimdb.repository.person.PersonRepositoryImpl
import com.davidbronn.personalimdb.repository.search.SearchMoviesApi
import com.davidbronn.personalimdb.repository.search.SearchRepository
import com.davidbronn.personalimdb.repository.search.SearchRepositoryImpl
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
    fun provideMovieListRepository(api: MoviesApi): MoviesRepository =
        MoviesRepositoryImpl(api)

    @Provides
    fun provideMovieDetailsRepository(
        dispatchers: DispatcherProvider,
        api: MovieDetailsApi,
        dao: LikedMovieDao
    ): DetailsRepository =
        DetailsRepositoryImpl(dispatchers, api, dao)

    @Provides
    fun provideSearchMoviesRepository(api: SearchMoviesApi): SearchRepository =
        SearchRepositoryImpl(api)

    @Provides
    fun providePersonRepository(
        dispatchers: DispatcherProvider,
        api: PersonApi
    ): PersonRepository =
        PersonRepositoryImpl(dispatchers, api)
}