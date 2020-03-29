package com.davidbronn.personalimdb.ui.movieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.MoviesListFragmentBinding
import com.davidbronn.personalimdb.di.KoinKeys
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.ui.interfaces.IRecyclerItemClickListener
import com.davidbronn.personalimdb.ui.moviedetails.MovieDetailsActivity
import com.davidbronn.personalimdb.utils.misc.NetworkState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent

class MoviesListFragment : Fragment(),
    IRecyclerItemClickListener, KoinComponent {

    private var adapter: MoviesAdapter? = null
    private lateinit var binding: MoviesListFragmentBinding
    private val viewModel: MoviesListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movies_list_fragment, container, false)
        getKoin().setProperty(KoinKeys.MOVIE_TYPE, getMovieType())
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setMoviesAdapter()
    }

    override fun onItemClicked(view: View?, item: Any) {
        val movieItem = item as ResultsItem
        MovieDetailsActivity.startMovieDetailsActivity(
            activity!!,
            movieItem
        )
    }

    private fun setObservers() {
        viewModel.getMovies()?.observe(viewLifecycleOwner, Observer { movieResults ->
            adapter?.submitList(movieResults)
        })

        viewModel.loadingInitial()?.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is NetworkState.Loading -> {
                    viewModel.setInitialLoading(state.loading)
                }
                is NetworkState.NetworkError -> {
                    viewModel.setInitialError(state.errorMessage)
                }
            }
        })

        viewModel.loadingAfter()?.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is NetworkState.Loading -> {
                    viewModel.setBottomLoading(state.loading)
                }
                is NetworkState.NetworkError -> {
                    viewModel.setBottomError(state.errorMessage)
                }
            }
        })
    }

    private fun setMoviesAdapter() {
        adapter = MoviesAdapter()
        adapter?.setItemClickListener(this)
        binding.rvMovies.adapter = adapter
        binding.rvMovies.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    private fun getMovieType() = arguments!!.getInt(MOVIE_TYPE)

    companion object {
        private const val MOVIE_TYPE = "movie_type"

        fun newInstance(movieType: Int): Fragment {
            return MoviesListFragment().apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_TYPE, movieType)
                }
            }
        }
    }
}
