package com.davidbronn.personalimdb.repository

interface IRepository<out T> {
    fun <T> onSuccess(t: T)
    fun onError(e: Throwable)
}