package com.davidbronn.personalimdb.ui.landing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.ActivityLandingBinding
import com.davidbronn.personalimdb.ui.search.SearchMoviesActivity

class LandingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_landing)

        setEvents()
        setMoviesAdapter()
    }

    private fun setEvents() {
        binding.fbSearch.setOnClickListener {
            // TODO Open with circular reveal animation
            SearchMoviesActivity.startMoviesSearchActivity(this)
        }
    }

    private fun setMoviesAdapter() {
        val moviesAdapter = LandingAdapter(supportFragmentManager)
        binding.vpMovies.adapter = moviesAdapter
        binding.tabLayout.setupWithViewPager(binding.vpMovies)
    }

    companion object {

        fun startLandingActivity(context: Context) {
            val intent = Intent(context, LandingActivity::class.java)
            context.startActivity(intent)
        }
    }
}
