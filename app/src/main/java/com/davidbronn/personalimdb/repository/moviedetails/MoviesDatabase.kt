package com.davidbronn.personalimdb.repository.moviedetails

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davidbronn.personalimdb.models.database.LikedMovie

/**
 * Created by Jude on 12/January/2020
 */
@Database(entities = [LikedMovie::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun likedMovieDao(): LikedMovieDao
}