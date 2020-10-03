package com.davidbronn.personalimdb.repository.details

import androidx.room.*
import com.davidbronn.personalimdb.models.database.LikedMovie

/**
 * Created by Jude on 12/January/2020
 */
@Dao
interface LikedMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: LikedMovie)

    @Delete
    fun deleteMovie(movie: LikedMovie)

    @Query("select * from liked_movies where movieId = :movieId")
    fun checkLikedMovie(movieId: Int): LikedMovie

    @Query("select count(*) from liked_movies")
    fun getMoviesCount(): Int
}