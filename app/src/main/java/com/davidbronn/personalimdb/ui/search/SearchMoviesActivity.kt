package com.davidbronn.personalimdb.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.ActivitySearchMoviesBinding
import com.davidbronn.personalimdb.utils.helpers.withSnack
import com.davidbronn.personalimdb.utils.misc.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchMoviesActivity : AppCompatActivity() {

    private var moviesAdapter: SearchMoviesAdapter? = null
    private val viewModel: SearchMoviesViewModel by viewModel()
    private lateinit var binding: ActivitySearchMoviesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_movies)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        setEvents()
        setObservers()
        setMoviesAdapter()
    }

    private fun setEvents() {
        binding.edMovies.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.edMovies.text?.let {
                    if (it.length > 3) {
                        viewModel.fetchMovies(it.toString())
                    } else {
                        binding.clRoot.withSnack(resources.getString(R.string.err_search_more_characters))
                    }
                }
            }
            false
        }
    }

    private fun setMoviesAdapter() {
        moviesAdapter = SearchMoviesAdapter()
        binding.rvMovies.adapter = moviesAdapter
        binding.rvMovies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun setObservers() {
        viewModel.event.observe(this, EventObserver { event ->
            when (event) {
                is SearchMoviesViewModel.SearchMoviesEvent.SnackBar -> {
                    binding.clRoot.withSnack(event.message)
                }
                is SearchMoviesViewModel.SearchMoviesEvent.MoviesList -> {
                    event.moviesList?.let {
                        moviesAdapter?.addItems(it)
                    }
                }
            }
        })
    }

    companion object {
        fun startMoviesSearchActivity(context: Context) {
            val intent = Intent(context, SearchMoviesActivity::class.java)
            context.startActivity(intent)
        }
    }
}
