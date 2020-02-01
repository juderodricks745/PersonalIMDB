package com.davidbronn.personalimdb.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
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

        lifecycleScope.launchWhenCreated {
            delay(4000)
            LandingActivity.startLandingActivity(this@SplashActivity)
        }
    }
}
