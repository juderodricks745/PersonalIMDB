package com.davidbronn.personalimdb.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.ActivitySearchMoviesBinding
import com.davidbronn.personalimdb.utils.withSnack
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
                        binding.clRoot.withSnack("Enter more than 3 characters!")
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
        viewModel.movieResults.observe(this, Observer { movies ->
            moviesAdapter?.addItems(movies)
        })
    }

    companion object {
        fun startMoviesSearchActivity(context: Context) {
            val intent = Intent(context, SearchMoviesActivity::class.java)
            context.startActivity(intent)
        }
    }
}
