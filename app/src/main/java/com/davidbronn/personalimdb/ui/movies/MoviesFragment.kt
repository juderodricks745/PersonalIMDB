package com.davidbronn.personalimdb.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.MoviesFragmentBinding
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.ui.interfaces.IRecyclerItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MoviesFragment : Fragment(),
    IRecyclerItemClickListener {

    lateinit var viewModel: MoviesViewModel
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var binding: MoviesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movies_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvents()
        setMoviesAdapter()
        observers()
    }

    override fun onItemClicked(view: View?, item: Any) {
        (item as ResultsItem).let { result ->
            val poster = view?.findViewById<ImageView>(R.id.ivPoster)!!
            val extras = FragmentNavigatorExtras(poster to result.movieID)
            val directions =
                MoviesFragmentDirections.toDetails(result.movieID, result.posterPath ?: "")
            findNavController().navigate(directions, extras)
        }
    }

    private fun observers() {
        lifecycleScope.launch {
            viewModel.fetchMovies().collectLatest {
                moviesAdapter.submitData(it)
            }
        }
    }

    private fun setMoviesAdapter() {
        moviesAdapter = MoviesAdapter()
        moviesAdapter.setItemClickListener(this)
        binding.rvMovies.apply {
            hasFixedSize()
            //layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            parentFragment?.postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                parentFragment?.startPostponedEnterTransition()
                true
            }
            adapter = moviesAdapter.withLoadStateFooter(
                footer = ReposLoadStateAdapter { moviesAdapter.retry() }
            )
        }
        moviesAdapter.addLoadStateListener { state ->
            Timber.i(state.toString())
        }
    }

    private fun initEvents() {
        binding.fabScrollToTop.setOnClickListener {
            binding.rvMovies.smoothScrollToPosition(0)
        }

        binding.fabSearch.setOnClickListener {
            findNavController().navigate(MoviesFragmentDirections.toSearch())
        }
    }
}