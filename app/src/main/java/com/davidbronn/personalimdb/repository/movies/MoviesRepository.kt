package com.davidbronn.personalimdb.repository.movies

import androidx.paging.PagingData
import com.davidbronn.personalimdb.models.network.ResultsItem
import kotlinx.coroutines.flow.Flow

/**
 * Created by Jude on 03/October/2020
 */
interface MoviesRepository {

    fun fetchMovies(): Flow<PagingData<ResultsItem>>
}