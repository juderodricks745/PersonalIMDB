package com.davidbronn.personalimdb.ui.moviedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.ActivityMovieDetailsBinding
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.ui.base.BaseActivity
import com.davidbronn.personalimdb.utils.decorations.SpacesItemDecoration
import com.davidbronn.personalimdb.utils.helpers.fadeEnterTransitionWithExclusion
import com.davidbronn.personalimdb.utils.helpers.viewCenterScale
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MovieDetailsActivity : BaseActivity() {

    private lateinit var castAdapter: MovieCastItemAdapter
    private lateinit var moviesAdapter: MovieCastItemAdapter

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setAppTheme()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        viewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        window.enterTransition = fadeEnterTransitionWithExclusion {
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }

        setEvents()
        setObservers()
        setCastAndSimilarMoviesAdapter()

        postponeEnterTransition()
        binding.sIvPoster.doOnPreDraw {
            supportStartPostponedEnterTransition()
        }
    }

    private fun setEvents() {
        viewModel.setMovieID(intent.getIntExtra(MOVIE_ID, MovieDetailsViewModel.INVALID_MOVIE_ID))

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
        viewModel.moviesListLiveData.observe(this, Observer { resource ->
            resource?.let { list ->
                moviesAdapter.setItems(list)
            }
        })

        viewModel.creditListLiveData.observe(this, Observer { resource ->
            resource?.let { list ->
                castAdapter.setItems(list)
            }
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
