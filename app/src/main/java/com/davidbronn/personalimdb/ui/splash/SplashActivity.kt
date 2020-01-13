package com.davidbronn.personalimdb.ui.splash

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.ActivitySplashBinding
import com.davidbronn.personalimdb.ui.landing.LandingActivity
import com.davidbronn.personalimdb.utils.EventObserver
import com.davidbronn.personalimdb.utils.withSnack
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModel()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        events()
        observers()
    }

    private fun observers() {
        viewModel.event.observe(this, EventObserver { event ->
            when(event) {
                SplashViewModel.SplashEvent.Proceed -> showNavigation()
                is SplashViewModel.SplashEvent.ShowToast -> binding.clRoot.withSnack(event.message)
            }
        })
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
