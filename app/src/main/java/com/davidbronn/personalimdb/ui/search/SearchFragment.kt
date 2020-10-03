package com.davidbronn.personalimdb.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.SearchFragmentBinding
import com.davidbronn.personalimdb.ui.interfaces.IRecyclerItemClickListener
import com.davidbronn.personalimdb.ui.movies.MoviesAdapter
import com.davidbronn.personalimdb.ui.movies.ReposLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchFragment : Fragment(),
    IRecyclerItemClickListener {

    private var searchJob: Job? = null
    lateinit var viewModel: SearchViewModel
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var binding: SearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvents()
        setMoviesAdapter()
    }

    private fun initEvents() {
        binding.edMovies.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val search = binding.edMovies.text.toString()
                if (search.length > 3) {
                    searchMovies(search)
                }
            }
            false
        }
    }

    private fun setMoviesAdapter() {
        moviesAdapter = MoviesAdapter()
        moviesAdapter.setItemClickListener(this)
        binding.rvMovies.apply {
            parentFragment?.postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                parentFragment?.startPostponedEnterTransition()
                true
            }
            adapter = moviesAdapter.withLoadStateFooter(
                footer = ReposLoadStateAdapter { moviesAdapter.retry() }
            )
        }
    }

    private fun searchMovies(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchMovies(query).collect {
                moviesAdapter.submitData(it)
            }
        }
    }

    override fun onItemClicked(view: View?, item: Any) {

    }
}