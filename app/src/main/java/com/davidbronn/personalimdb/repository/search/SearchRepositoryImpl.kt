package com.davidbronn.personalimdb.repository.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.ui.search.SearchPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Jude on 13/January/2020
 */
class SearchRepositoryImpl @Inject constructor(private val api: SearchMoviesApi) :
    SearchRepository {

    override fun searchMovies(queryString: String): Flow<PagingData<ResultsItem>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                SearchPagingSource(
                    queryString,
                    api
                )
            }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}