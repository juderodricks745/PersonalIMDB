package com.davidbronn.personalimdb.utils.misc

/**
 * Created by Jude on 18/January/2020
 */
sealed class NetworkState {
    data class Loading(val loading: Boolean) : NetworkState()
    class NetworkError(val errorMessage: String) : NetworkState()
}