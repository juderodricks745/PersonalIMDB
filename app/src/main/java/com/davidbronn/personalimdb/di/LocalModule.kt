package com.davidbronn.personalimdb.di

import android.content.Context
import androidx.room.Room
import com.davidbronn.personalimdb.repository.moviedetails.LikedMovieDao
import com.davidbronn.personalimdb.repository.moviedetails.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * Created by Jude on 18/June/2020
 */
@InstallIn(ApplicationComponent::class)
@Module
class LocalModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MoviesDatabase::class.java,
            "movies-db"
        ).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideDao(db: MoviesDatabase): LikedMovieDao = db.likedMovieDao()
}