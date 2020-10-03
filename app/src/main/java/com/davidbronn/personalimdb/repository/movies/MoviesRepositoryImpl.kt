package com.davidbronn.personalimdb.repository.movies

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.ui.movies.MoviesPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Jude on 14/June/2020
 */
class MoviesRepositoryImpl @Inject constructor(private val api: MoviesApi) :
    MoviesRepository {

    override fun fetchMovies(): Flow<PagingData<ResultsItem>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviesPagingSource(api) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}