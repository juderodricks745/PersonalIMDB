package com.davidbronn.personalimdb.ui.base

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.repository.landing.PreferenceHelper
import org.koin.android.ext.android.inject

open class BaseActivity : AppCompatActivity() {

    private val themeRepo: PreferenceHelper by inject()

    fun setAppTheme() {
        val nightMode = themeRepo.getTheme()
        setTheme(
            when (nightMode) {
                PreferenceHelper.NOT_DARK_MODE ->
                    R.style.AppTheme

                PreferenceHelper.IS_DARK_MODE ->
                    R.style.DarkTheme
                else -> {
                    val currentNightMode =
                        resources!!.configuration!!.uiMode and Configuration.UI_MODE_NIGHT_MASK
                    when (currentNightMode) {
                        Configuration.UI_MODE_NIGHT_YES -> {
                            themeRepo.setTheme(PreferenceHelper.IS_DARK_MODE)
                            R.style.DarkTheme
                        }
                        Configuration.UI_MODE_NIGHT_NO -> {
                            themeRepo.setTheme(PreferenceHelper.NOT_DARK_MODE)
                            R.style.AppTheme
                        }
                        else -> {
                            themeRepo.setTheme(PreferenceHelper.NOT_DARK_MODE)
                            R.style.AppTheme
                        }
                    }
                }
            }
        )
    }
}
