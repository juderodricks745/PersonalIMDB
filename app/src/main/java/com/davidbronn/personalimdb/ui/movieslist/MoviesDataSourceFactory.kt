package com.davidbronn.personalimdb.ui.movieslist

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.davidbronn.personalimdb.models.network.ResultsItem

/**
 * Created by Jude on 12/January/2020
 */
class MoviesDataSourceFactory(val dataSource: MoviesDataSource)
    : DataSource.Factory<Int, ResultsItem>() {

    private val showsDataSourceLiveData: MutableLiveData<MoviesDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, ResultsItem> {
        showsDataSourceLiveData.postValue(dataSource)
        return dataSource
    }
}