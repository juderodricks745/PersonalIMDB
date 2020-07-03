package com.davidbronn.personalimdb.repository.searchmovies

import androidx.lifecycle.LiveData
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.utils.misc.Resource

/**
 * Created by Jude on 13/January/2020
 */
interface SearchMoviesRepository {

    fun fetchMoviesByLiveData(searchText: String): LiveData<Resource<List<ResultsItem>>>
}