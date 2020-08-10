package com.davidbronn.personalimdb.repository.searchmovies

import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.utils.misc.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Jude on 13/January/2020
 */
interface SearchMoviesRepository {

    fun fetchMoviesByLiveData(searchText: String): Flow<Resource<List<ResultsItem>>>
}