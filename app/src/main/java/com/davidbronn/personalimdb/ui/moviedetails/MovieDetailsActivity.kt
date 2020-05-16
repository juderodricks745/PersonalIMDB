package com.davidbronn.personalimdb.ui.moviedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.ActivityMovieDetailsBinding
import com.davidbronn.personalimdb.di.KoinKeys
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.utils.decorations.SpacesItemDecoration
import com.davidbronn.personalimdb.utils.helpers.viewCenterScale
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

        postponeEnterTransition()
        binding.sIvPoster.doOnPreDraw {
            supportStartPostponedEnterTransition()
        }
    }

    private fun setEvents() {
        binding.ivBack.setOnClickListener {
            supportFinishAfterTransition()
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
                binding.cvCast.startCenterScaling()
            }
        })

        viewModel.showMoviesIfAvailable.observe(this, Observer {
            if (it) {
                binding.cvSimilarMovies.startCenterScaling()
            }
        })
    }

    private fun getMovieId(): Int = intent.extras?.getInt(MOVIE_ID)!!

    private fun getMoviePosterUrl(): String = intent.extras?.getString(MOVIE_URL)!!

    private fun View.startCenterScaling() {
        this.viewCenterScale {
            duration = 600
            interpolator = OvershootInterpolator()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFinishAfterTransition()
    }

    companion object {
        const val MOVIE_ID = "movie_id"
        const val MOVIE_URL = "movie_url"

        fun startMovieDetailsActivity(
            context: Context,
            movieResultsItem: ResultsItem,
            options: Bundle
        ) {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID, movieResultsItem.id!!)
            intent.putExtra(MOVIE_URL, movieResultsItem.posterPath!!)
            context.startActivity(intent, options)
        }
    }
}
