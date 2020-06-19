package com.davidbronn.personalimdb.ui.base

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.repository.landing.PreferenceHelper
import com.davidbronn.personalimdb.repository.landing.PreferenceHelperImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var themeRepo: PreferenceHelper

    fun setAppTheme() {
        GlobalScope.launch(Dispatchers.Main) {
            delay(100)
            val nightMode = themeRepo.getTheme()
            setTheme(
                when (nightMode) {
                    PreferenceHelperImpl.NOT_DARK_MODE ->
                        R.style.AppTheme

                    PreferenceHelperImpl.IS_DARK_MODE ->
                        R.style.DarkTheme
                    else -> {
                        val currentNightMode =
                            resources!!.configuration!!.uiMode and Configuration.UI_MODE_NIGHT_MASK
                        when (currentNightMode) {
                            Configuration.UI_MODE_NIGHT_YES -> {
                                themeRepo.setTheme(PreferenceHelperImpl.IS_DARK_MODE)
                                R.style.DarkTheme
                            }
                            Configuration.UI_MODE_NIGHT_NO -> {
                                themeRepo.setTheme(PreferenceHelperImpl.NOT_DARK_MODE)
                                R.style.AppTheme
                            }
                            else -> {
                                themeRepo.setTheme(PreferenceHelperImpl.NOT_DARK_MODE)
                                R.style.AppTheme
                            }
                        }
                    }
                }
            )
        }
    }
}
