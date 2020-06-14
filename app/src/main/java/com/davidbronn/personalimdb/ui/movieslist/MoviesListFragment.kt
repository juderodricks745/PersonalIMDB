package com.davidbronn.personalimdb.ui.movieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.MoviesListFragmentBinding
import com.davidbronn.personalimdb.di.KoinKeys
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.ui.interfaces.IRecyclerItemClickListener
import com.davidbronn.personalimdb.ui.moviedetails.MovieDetailsActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent

class MoviesListFragment : Fragment(),
    IRecyclerItemClickListener, KoinComponent {

    private lateinit var adapter: MoviesAdapter
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
