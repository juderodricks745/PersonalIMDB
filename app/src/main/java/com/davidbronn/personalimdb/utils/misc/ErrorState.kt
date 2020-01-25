package com.davidbronn.personalimdb.utils.misc

data class ErrorState(
    val listener: ErrorStateRetryListener,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = ""
) {
    fun onRetry() {
        listener.onRetry()
    }

    interface ErrorStateRetryListener {
        fun onRetry()
    }
}