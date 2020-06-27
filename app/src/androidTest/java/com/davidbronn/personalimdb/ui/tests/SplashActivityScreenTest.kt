package com.davidbronn.personalimdb.ui.tests

import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen.Companion.idle
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.davidbronn.personalimdb.ui.landing.LandingActivity
import com.davidbronn.personalimdb.ui.screens.SplashActivityScreen
import com.davidbronn.personalimdb.ui.splash.SplashActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Jude on 26/June/2020
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class SplashActivityScreenTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(SplashActivity::class.java)

    @Before
    fun before() {
        Intents.init()
    }

    @After
    fun after() {
        Intents.release()
    }

    @Test
    fun test_Splash_Screen_Visibility() {
        onScreen<SplashActivityScreen> {
            lblPersonalIMDB.isVisible()
        }
    }

    @Test
    fun test_Navigation_To_LandingPage() {
        onScreen<SplashActivityScreen> {
            lblPersonalIMDB.isVisible()
            idle(3000)
            intended(hasComponent(LandingActivity::class.java.name))
        }
    }
}