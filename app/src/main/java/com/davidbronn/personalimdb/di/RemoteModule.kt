package com.davidbronn.personalimdb.di

import com.davidbronn.personalimdb.BuildConfig
import com.davidbronn.personalimdb.repository.details.MovieDetailsApi
import com.davidbronn.personalimdb.repository.movies.MoviesApi
import com.davidbronn.personalimdb.repository.person.PersonApi
import com.davidbronn.personalimdb.repository.search.SearchMoviesApi
import com.davidbronn.personalimdb.utils.helpers.RequestInterceptor
import com.davidbronn.personalimdb.utils.helpers.apiInstance
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
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
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
    fun provideMoviesListService(retrofit: Retrofit): MoviesApi =
        apiInstance(retrofit)

    @Provides
    @Singleton
    fun provideMoviesDetailsService(retrofit: Retrofit): MovieDetailsApi =
        apiInstance(retrofit)

    @Provides
    @Singleton
    fun provideMoviesSearchService(retrofit: Retrofit): SearchMoviesApi =
        apiInstance(retrofit)

    @Provides
    @Singleton
    fun providePersonService(retrofit: Retrofit): PersonApi = apiInstance(retrofit)
}