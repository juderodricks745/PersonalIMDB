package com.davidbronn.personalimdb.ui.landing

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.ActivityLandingBinding
import com.davidbronn.personalimdb.ui.movieslist.MoviesListFragment

class LandingActivity : AppCompatActivity() {

    private val titles = mutableListOf("Popular Movies", "Top Rated Movies")
    private lateinit var binding: ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_landing)

        setMoviesAdapter()
    }

    private fun setMoviesAdapter() {
        val moviesAdapter = LandingAdapter(titles, supportFragmentManager)
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
