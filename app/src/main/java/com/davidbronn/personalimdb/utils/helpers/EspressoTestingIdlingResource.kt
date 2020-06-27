package com.davidbronn.personalimdb.utils.helpers

import androidx.test.espresso.IdlingResource

import androidx.test.espresso.idling.CountingIdlingResource

/**
 * Created by Jude on 25/June/2020
 */
object EspressoTestingIdlingResource {

    private const val RESOURCE = "GLOBAL"

    private val mCountingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        mCountingIdlingResource.increment()
    }

    fun decrement() {
        mCountingIdlingResource.decrement()
    }

    fun getIdlingResource(): IdlingResource? {
        return mCountingIdlingResource
    }
}