package com.davidbronn.personalimdb.models.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Jude on 12/January/2020
 */
@Entity(tableName = "liked_movies")
data class LikedMovie (@PrimaryKey val movieId: Int)