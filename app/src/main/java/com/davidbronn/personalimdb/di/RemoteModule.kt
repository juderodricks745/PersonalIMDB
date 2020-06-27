package com.davidbronn.personalimdb.di

import com.davidbronn.personalimdb.BuildConfig
import com.davidbronn.personalimdb.repository.moviedetails.LikedMovieDao
import com.davidbronn.personalimdb.repository.moviedetails.MovieDetailsApi
import com.davidbronn.personalimdb.repository.moviedetails.MovieDetailsRepository
import com.davidbronn.personalimdb.repository.moviedetails.MovieDetailsRepositoryImpl
import com.davidbronn.personalimdb.repository.movieslist.MoviesListApi
import com.davidbronn.personalimdb.repository.searchmovies.SearchMoviesApi
import com.davidbronn.personalimdb.repository.searchmovies.SearchMoviesRepository
import com.davidbronn.personalimdb.repository.searchmovies.SearchMoviesRepositoryImpl
import com.davidbronn.personalimdb.utils.helpers.RequestInterceptor
import com.davidbronn.personalimdb.utils.misc.DefaultDispatcherProvider
import com.davidbronn.personalimdb.utils.misc.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Jude on 17/June/2020
 */
@InstallIn(ApplicationComponent::class)
@Module
class RemoteModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(RequestInterceptor())
            .build()
    } else OkHttpClient
        .Builder()
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMoviesListService(retrofit: Retrofit): MoviesListApi = retrofit.create(MoviesListApi::class.java)

    @Provides
    @Singleton
    fun provideMoviesDetailsService(retrofit: Retrofit): MovieDetailsApi = retrofit.create(MovieDetailsApi::class.java)

    @Provides
    @Singleton
    fun provideMoviesSearchService(retrofit: Retrofit): SearchMoviesApi = retrofit.create(SearchMoviesApi::class.java)

    @Provides
    fun provideMovieDetailsRepository(api: MovieDetailsApi, dao: LikedMovieDao): MovieDetailsRepository =
        MovieDetailsRepositoryImpl(api, dao)

    @Provides
    fun provideDispatchers(): DispatcherProvider =
        DefaultDispatcherProvider()

    @Provides
    fun provideSearchMoviesRepository(dispatchers: DispatcherProvider, api: SearchMoviesApi): SearchMoviesRepository =
        SearchMoviesRepositoryImpl(dispatchers, api)
}