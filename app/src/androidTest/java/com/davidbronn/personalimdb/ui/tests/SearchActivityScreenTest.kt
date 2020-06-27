package com.davidbronn.personalimdb.ui.tests

import androidx.test.espresso.IdlingRegistry
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.davidbronn.personalimdb.ui.screens.SearchActivityScreen
import com.davidbronn.personalimdb.ui.search.SearchMoviesActivity
import com.davidbronn.personalimdb.utils.helpers.EspressoTestingIdlingResource.getIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Jude on 25/June/2020
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class SearchActivityScreenTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(SearchMoviesActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(getIdlingResource())
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(getIdlingResource())
    }

    @Test
    fun test() {
        onScreen<SearchActivityScreen> {
            edMovies.typeText("Avengers")
            edMovies.pressImeAction()
            rvMovies.isVisible()
            kProgressBar.isGone()
        }
    }
}