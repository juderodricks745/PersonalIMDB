package com.davidbronn.personalimdb.ui.movieslist

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.movieslist.MoviesApi

/**
 * Created by Jude on 12/January/2020
 */
class MoviesDataSourceFactory(val type: String, private val moviesApi: MoviesApi)
    : DataSource.Factory<Int, ResultsItem>() {

    private var moviesDataSource: MoviesDataSource? = null
    private var moviesDataSourceLiveData = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, ResultsItem> {
        moviesDataSource = MoviesDataSource(type, moviesApi)
        moviesDataSourceLiveData.postValue(moviesDataSource)
        return moviesDataSource!!
    }

    fun movies(): MutableLiveData<MoviesDataSource> = moviesDataSourceLiveData
}