package com.davidbronn.personalimdb.ui.splash

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.ActivitySplashBinding
import com.davidbronn.personalimdb.ui.landing.LandingActivity
import kotlinx.coroutines.delay

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this

        events()
        lifecycleScope.launchWhenCreated {
            delay(1000)
            showNavigation()
        }
    }

    private fun events() {
        binding.fabStart.setOnClickListener {
            LandingActivity.startLandingActivity(this)
        }
    }

    private fun showNavigation() {
        val transition = Fade().apply {
            duration = 1000
            addTarget(R.id.fabStart)
        }
        TransitionManager.beginDelayedTransition(binding.clRoot, transition)
        binding.fabStart.visibility = View.VISIBLE
    }
}
