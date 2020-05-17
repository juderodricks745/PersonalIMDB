package com.davidbronn.personalimdb.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.ActivitySplashBinding
import com.davidbronn.personalimdb.repository.landing.PreferenceHelper
import com.davidbronn.personalimdb.ui.base.BaseActivity
import com.davidbronn.personalimdb.ui.landing.LandingActivity
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject

class SplashActivity : BaseActivity() {

    private val themeRepo: PreferenceHelper by inject()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setAppTheme()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this

        lifecycleScope.launchWhenCreated {
            delay(3500)
            LandingActivity.startLandingActivity(this@SplashActivity)
            finish()
        }
    }

    companion object {
        fun startSplashActivity(context: Context) {
            val intent = Intent(context, SplashActivity::class.java)
            context.startActivity(intent)
        }
    }
}
