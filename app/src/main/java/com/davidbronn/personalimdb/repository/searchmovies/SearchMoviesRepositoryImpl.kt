package com.davidbronn.personalimdb.repository.searchmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.models.network.StatusModel
import com.davidbronn.personalimdb.utils.helpers.jsonify
import com.davidbronn.personalimdb.utils.misc.DispatcherProvider
import com.davidbronn.personalimdb.utils.misc.Resource
import com.davidbronn.personalimdb.utils.misc.Status
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Jude on 13/January/2020
 */
class SearchMoviesRepositoryImpl @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val api: SearchMoviesApi
) : SearchMoviesRepository {

    override fun fetchMoviesByLiveData(searchText: String): LiveData<Resource<List<ResultsItem>>> {
        return liveData(dispatchers.io()) {
            Timber.i("Called initially")
            delay(500)
            emit(Resource(Status.LOADING, null, ""))
            val response = api.fetchMoviesByTextAsync(searchText)
            if (response.isSuccessful) {
                val movieBody = response.body()
                movieBody?.let {
                    emit(Resource(Status.SUCCESS, it.results!!, ""))
                }
            } else {
                val statusModel = response.errorBody()?.string()?.jsonify<StatusModel>()
                emit(Resource(Status.ERROR, null, statusModel?.statusMessage!!))
            }
        }
    }
}