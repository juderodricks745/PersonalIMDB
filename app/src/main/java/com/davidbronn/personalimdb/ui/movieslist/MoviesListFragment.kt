package com.davidbronn.personalimdb.ui.movieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.MoviesListFragmentBinding
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.ui.interfaces.IRecyclerItemClickListener
import com.davidbronn.personalimdb.ui.moviedetails.MovieDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesListFragment : Fragment(),
    IRecyclerItemClickListener {

    private lateinit var adapter: MoviesAdapter
    lateinit var viewModel: MoviesListViewModel
    private lateinit var binding: MoviesListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movies_list_fragment, container, false)
        viewModel = ViewModelProvider(this).get(MoviesListViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMoviesAdapter()
        setObservers()
    }

    override fun onItemClicked(view: View?, item: Any) {
        val movieItem = item as ResultsItem

        val posterImage = view?.findViewById<ImageView>(R.id.sIvPoster)!!
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            activity!!,
            posterImage,
            posterImage.transitionName
        ).toBundle()!!
        MovieDetailsActivity.startMovieDetailsActivity(
            activity!!,
            movieItem,
            options
        )
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.fetchMovies().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setMoviesAdapter() {
        adapter = MoviesAdapter()
        adapter.setItemClickListener(this)
        binding.rvMovies.adapter = adapter.withLoadStateFooter(
            footer = ReposLoadStateAdapter { adapter.retry() }
        )
    }

    companion object {

        fun newInstance(movieType: Int): Fragment {
            return MoviesListFragment().apply {
                arguments = Bundle().apply {
                    putInt(MoviesListViewModel.Keys.MOVIE_TYPE, movieType)
                }
            }
        }
    }
}
