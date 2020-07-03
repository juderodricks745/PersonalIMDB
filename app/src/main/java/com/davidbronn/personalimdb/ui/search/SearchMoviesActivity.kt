package com.davidbronn.personalimdb.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.ActivitySearchMoviesBinding
import com.davidbronn.personalimdb.ui.base.BaseActivity
import com.davidbronn.personalimdb.utils.helpers.EspressoTestingIdlingResource
import com.davidbronn.personalimdb.utils.helpers.withSnack
import com.davidbronn.personalimdb.utils.misc.EventObserver
import com.davidbronn.personalimdb.utils.misc.RetryCallback
import com.davidbronn.personalimdb.utils.misc.Status

class SearchMoviesActivity : BaseActivity() {

    private var moviesAdapter: SearchMoviesAdapter? = null
    private lateinit var binding: ActivitySearchMoviesBinding
    private val viewModel: SearchMoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setAppTheme()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_movies)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        bindObservers()
        setMoviesAdapter()
    }

    private fun bindObservers() {
        binding.results = viewModel.getResource()

        binding.callBack = object : RetryCallback {
            override fun retry() {
                // TODO: Some retry for face save
            }
        }

        viewModel.getResource().observe(this, Observer { resources ->
            resources?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        EspressoTestingIdlingResource.decrement()
                        resource.data?.let { movieResults ->
                            moviesAdapter?.addItems(movieResults)
                        }
                    }
                    Status.ERROR -> {
                        EspressoTestingIdlingResource.decrement()
                    }
                    Status.LOADING -> {
                        EspressoTestingIdlingResource.increment()
                    }
                }
            }
        })

        viewModel.event.observe(this, EventObserver { event ->
            when (event) {
                is SearchMoviesViewModel.SearchMoviesEvent.SnackBar -> {
                    binding.clRoot.withSnack(event.message)
                }
            }
        })

        binding.edMovies.addTextChangedListener {
            if (it.toString().length > 2) {
                viewModel.setMovieSearchParams(it.toString())
            }
        }
    }

    private fun setMoviesAdapter() {
        moviesAdapter = SearchMoviesAdapter()
        binding.rvMovies.adapter = moviesAdapter
    }

    companion object {
        fun startMoviesSearchActivity(context: Context) {
            val intent = Intent(context, SearchMoviesActivity::class.java)
            context.startActivity(intent)
        }
    }
}
