package com.davidbronn.personalimdb.dao

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.davidbronn.personalimdb.models.database.LikedMovie
import com.davidbronn.personalimdb.repository.details.LikedMovieDao
import com.davidbronn.personalimdb.repository.details.MoviesDatabase
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Created by Jude on 23/June/2020
 */

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class LikedMovieDaoTest {

    private val movieID = 1000

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var moviesDb: MoviesDatabase
    private lateinit var likedMovieDao: LikedMovieDao

    @Before
    fun setUp() {
        // Always use inMemoryDatabaseBuilder while testing
        moviesDb = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoviesDatabase::class.java
        ).allowMainThreadQueries().build()
        likedMovieDao = moviesDb.likedMovieDao()
    }

    @After
    fun closeDatabase() {
        moviesDb.close()
    }

    @Test
    fun `insert movie & check count`() {
        likedMovieDao.insertMovie(LikedMovie(movieID))
        val count = likedMovieDao.getMoviesCount()
        Assert.assertEquals(count, 1)
    }

    @Test
    fun `insert movie, then check if that movieID returns a non null value`() {
        likedMovieDao.insertMovie(LikedMovie(movieID))
        val movieItem = likedMovieDao.checkLikedMovie(movieID)
        Assert.assertNotNull(movieItem)
    }

    @Test
    fun `insert movie, then delete the respective movieID`() {
        likedMovieDao.insertMovie(LikedMovie(movieID))
        likedMovieDao.deleteMovie(LikedMovie(movieID))
        val movieItem = likedMovieDao.checkLikedMovie(movieID)
        Assert.assertNull(movieItem)
    }
}