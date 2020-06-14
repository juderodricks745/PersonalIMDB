package com.davidbronn.personalimdb.repository.movieslist

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.davidbronn.personalimdb.models.network.ResultsItem
import kotlinx.coroutines.flow.Flow

/**
 * Created by Jude on 14/June/2020
 */
class MoviesListRepository(
    private val type: Int,
    private val api: MoviesListApi
) {

    fun fetchMovies(): Flow<PagingData<ResultsItem>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviesListPagingSource(type, api) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}