package com.davidbronn.personalimdb.repository.movieslist

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.davidbronn.personalimdb.models.network.ResultsItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Jude on 14/June/2020
 */
class MoviesListRepository @Inject constructor(private val api: MoviesListApi) {

    fun fetchMovies(type: Int): Flow<PagingData<ResultsItem>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviesListPagingSource(type, api) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}