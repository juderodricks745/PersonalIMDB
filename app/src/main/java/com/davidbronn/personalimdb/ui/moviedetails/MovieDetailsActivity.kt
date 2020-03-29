package com.davidbronn.personalimdb.ui.moviedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.ActivityMovieDetailsBinding
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.utils.helpers.scale
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsActivity : AppCompatActivity() {

    private var castAdapter: MovieCastItemAdapter? = null
    private var moviesAdapter: MovieCastItemAdapter? = null

    private val viewModel: MovieDetailsViewModel by viewModel()
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        viewModel.fetchMovieAndRelatedDetails(getMovieItem())

        setEvents()
        setObservers()
        setCastAndSimilarMoviesAdapter()
    }

    private fun setEvents() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setCastAndSimilarMoviesAdapter() {
        castAdapter = MovieCastItemAdapter()
        binding.rvCast.adapter = castAdapter
        binding.rvCast.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        moviesAdapter = MovieCastItemAdapter()
        binding.rvSimilarMovies.adapter = moviesAdapter
        binding.rvSimilarMovies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setObservers() {
        viewModel.moviesListLiveData.observe(this, Observer { items ->
            moviesAdapter?.setItems(items)
        })

        viewModel.creditListLiveData.observe(this, Observer { items ->
            castAdapter?.setItems(items)
        })

        viewModel.showCastsIfAvailable.observe(this, Observer {
            if (it) {
                binding.cvCast.scale()
            }
        })

        viewModel.showMoviesIfAvailable.observe(this, Observer {
            if (it) {
                binding.cvSimilarMovies.scale()
            }
        })
    }

    private fun getMovieItem(): ResultsItem = intent.extras?.getParcelable(MOVIE_ITEM)!!

    companion object {
        const val MOVIE_ITEM = "movie_item"

        fun startMovieDetailsActivity(context: Context, resultItem: ResultsItem) {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ITEM, resultItem)
            context.startActivity(intent)
        }
    }
}
