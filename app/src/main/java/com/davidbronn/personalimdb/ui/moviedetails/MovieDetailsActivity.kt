package com.davidbronn.personalimdb.ui.moviedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Transition
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.ActivityMovieDetailsBinding
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

        viewModel.fetchMovieAndRelatedDetails(getMovieId())

        setEvents()
        setObservers()
        setMovieCastAdapter()
        supportPostponeEnterTransition()
    }

    private fun setEvents() {
        binding.ivBack.setOnClickListener {
            supportFinishAfterTransition()
        }
    }

    private fun setMovieCastAdapter() {
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

        viewModel.showImageWithTransition.observe(this, Observer {
            binding.sIvPoster.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        binding.sIvPoster.viewTreeObserver.removeOnPreDrawListener(this)
                        window.sharedElementEnterTransition = enterTransition()
                        supportStartPostponedEnterTransition()
                        return true
                    }
                }
            )
        })
    }

    private fun getMovieId(): Int = intent.extras?.getInt(MOVIE_ID)!!

    private fun enterTransition(): Transition = ChangeBounds().apply {
        duration = 300
        interpolator = AccelerateDecelerateInterpolator()
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
        super.onBackPressed()
    }

    companion object {

        const val MOVIE_ID = "movie_id"

        fun startMovieDetailsActivity(context: Context, movieId: Int, bundle: Bundle?) {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID, movieId)
            context.startActivity(intent, bundle)
        }
    }
}
