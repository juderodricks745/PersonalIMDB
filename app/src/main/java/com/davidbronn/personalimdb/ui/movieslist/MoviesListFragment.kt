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
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.repository.movieslist.MoviesApi
import com.davidbronn.personalimdb.ui.moviedetails.MovieDetailsActivity
import com.davidbronn.personalimdb.utils.IRecyclerItemClickListener
import com.davidbronn.personalimdb.utils.getViewModel
import org.koin.android.ext.android.inject

class MoviesListFragment : Fragment(), IRecyclerItemClickListener {

    private val api: MoviesApi by inject()
    private var adapter: MoviesAdapter? = null
    private var viewModel: MoviesListViewModel? = null
    private lateinit var binding: MoviesListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movies_list_fragment, container, false)
        viewModel = getViewModel { MoviesListViewModel(api, arguments?.getString(MOVIE_TYPE)!!) }
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setMoviesAdapter()
    }

    override fun onItemClicked(view: View?, item: Any) {
        val movieItem = item as ResultsItem
        MovieDetailsActivity.startMovieDetailsActivity(activity!!, movieItem.id!!)
    }

    private fun setObservers() {
        viewModel?.getMovies()?.observe(viewLifecycleOwner, Observer { movieResults ->
            adapter?.submitList(movieResults)
        })
    }

    private fun setMoviesAdapter() {
        adapter = MoviesAdapter()
        adapter?.setItemClickListener(this)
        binding.rvMovies.adapter = adapter
        binding.rvMovies.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    companion object {
        private const val MOVIE_TYPE = "movie_type"

        fun newInstance(movieType: String): Fragment {
            return MoviesListFragment().apply {
                arguments = Bundle().apply {
                    putString(MOVIE_TYPE, movieType)
                }
            }
        }
    }
}
