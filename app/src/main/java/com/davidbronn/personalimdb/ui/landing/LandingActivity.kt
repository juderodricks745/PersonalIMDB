package com.davidbronn.personalimdb.ui.landing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.ChangeClipBounds
import android.transition.Explode
import android.transition.TransitionSet
import androidx.databinding.DataBindingUtil
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.ActivityLandingBinding
import com.davidbronn.personalimdb.repository.landing.PreferenceHelper
import com.davidbronn.personalimdb.repository.landing.PreferenceHelper.Companion.IS_DARK_MODE
import com.davidbronn.personalimdb.repository.landing.PreferenceHelper.Companion.NOT_DARK_MODE
import com.davidbronn.personalimdb.ui.base.BaseActivity
import com.davidbronn.personalimdb.ui.search.SearchMoviesActivity
import com.davidbronn.personalimdb.ui.splash.SplashActivity
import org.koin.android.ext.android.inject

class LandingActivity : BaseActivity() {

    private lateinit var binding: ActivityLandingBinding
    private val themeRepo: PreferenceHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppTheme()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_landing)

        window.enterTransition = Explode()
        window.exitTransition = Explode()
        window.sharedElementExitTransition = TransitionSet().apply {
            addTransition(ChangeClipBounds())
        }

        setEvents()
        setMoviesAdapter()
        setUpThemeToggleImage()
    }

    private fun setEvents() {
        binding.fbSearch.setOnClickListener {
            SearchMoviesActivity.startMoviesSearchActivity(this)
        }

        binding.ivThemeToggle.setOnClickListener {
            toggleTheme()
        }
    }

    private fun setMoviesAdapter() {
        val moviesAdapter = LandingAdapter(supportFragmentManager)
        binding.vpMovies.adapter = moviesAdapter
        binding.tabLayout.setupWithViewPager(binding.vpMovies)
    }

    private fun toggleTheme() {
        restartActivity()
    }

    private fun restartActivity() {
        val appTheme = themeRepo.getTheme()
        if (appTheme == IS_DARK_MODE) {
            themeRepo.setTheme(NOT_DARK_MODE)
        } else {
            themeRepo.setTheme(IS_DARK_MODE)
        }
        recreate()
    }

    private fun setUpThemeToggleImage() {
        binding.ivThemeToggle.apply {
            val appTheme = themeRepo.getTheme()
            if (appTheme == IS_DARK_MODE) {
                setImageResource(R.drawable.ic_light)
            } else {
                setImageResource(R.drawable.ic_dark)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        SplashActivity.startSplashActivity(this)
    }

    companion object {

        fun startLandingActivity(context: Context) {
            val intent = Intent(context, LandingActivity::class.java)
            context.startActivity(intent)
        }
    }
}
