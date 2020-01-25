package com.davidbronn.personalimdb.repository.searchmovies

import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.utils.misc.Result

/**
 * Created by Jude on 13/January/2020
 */
interface SearchMoviesRepository {

    suspend fun fetchMovies(searchText: String,
                            includeAdult: String)
            : Result<List<ResultsItem?>?>?
}