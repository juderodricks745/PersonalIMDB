package com.davidbronn.personalimdb.ui.moviedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.ActivityMovieDetailsBinding
import com.davidbronn.personalimdb.di.KoinKeys
import com.davidbronn.personalimdb.utils.decorations.SpacesItemDecoration
import com.davidbronn.personalimdb.utils.helpers.visible
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent

class MovieDetailsActivity : AppCompatActivity(), KoinComponent {

    private var castAdapter: MovieCastItemAdapter? = null
    private var moviesAdapter: MovieCastItemAdapter? = null

    private val viewModel: MovieDetailsViewModel by viewModel()
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        getKoin().setProperty(KoinKeys.MOVIE_ID, getMovieId())
        binding.lifecycleOwner = this
        binding.vm = viewModel

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
        binding.rvCast.addItemDecoration(SpacesItemDecoration(resources.getDimensionPixelSize(R.dimen.dp_10)))
        binding.rvCast.layoutManager =
            GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)

        moviesAdapter = MovieCastItemAdapter()
        binding.rvSimilarMovies.adapter = moviesAdapter
        binding.rvSimilarMovies.addItemDecoration(
            SpacesItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.dp_10
                )
            )
        )
        binding.rvSimilarMovies.layoutManager =
            GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
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
                binding.cvCast.visible()
            }
        })

        viewModel.showMoviesIfAvailable.observe(this, Observer {
            if (it) {
                binding.cvSimilarMovies.visible()
            }
        })
    }

    private fun getMovieId(): Int = intent.extras?.getInt(MOVIE_ID)!!

    companion object {
        const val MOVIE_ID = "movie_id"

        fun startMovieDetailsActivity(context: Context, movieID: Int) {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID, movieID)
            context.startActivity(intent)
        }
    }
}
