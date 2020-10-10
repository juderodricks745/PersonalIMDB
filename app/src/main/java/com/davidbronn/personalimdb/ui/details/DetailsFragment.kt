package com.davidbronn.personalimdb.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.DetailsFragmentBinding
import com.davidbronn.personalimdb.models.network.MovieCastItem
import com.davidbronn.personalimdb.ui.interfaces.IRecyclerItemClickListener
import com.davidbronn.personalimdb.utils.extensions.urlWithListener
import com.davidbronn.personalimdb.utils.helpers.gridSpan
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailsFragment : Fragment(), IRecyclerItemClickListener {

    lateinit var viewModel: DetailsViewModel
    private lateinit var binding: DetailsFragmentBinding
    private lateinit var castAdapter: MovieCastItemAdapter
    private lateinit var moviesAdapter: MovieCastItemAdapter

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        binding.vm = viewModel
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.change_image_transform)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setEvents()
        setObservers()
        setCastAndSimilarMoviesAdapter()
    }

    private fun setEvents() {
        viewModel.setMovieID(args.movieID.toInt())
        postponeEnterTransition()
        binding.sIvPoster.urlWithListener(args.picture) {
            startPostponedEnterTransition()
        }
    }

    private fun setCastAndSimilarMoviesAdapter() {
        castAdapter = MovieCastItemAdapter()
        castAdapter.setItemClickListener(this)
        binding.rvCast.adapter = castAdapter
        binding.rvCast.gridSpan(requireActivity(), 3)

        moviesAdapter = MovieCastItemAdapter()
        binding.rvSimilarMovies.adapter = moviesAdapter
        binding.rvSimilarMovies.gridSpan(requireActivity(), 3)
    }

    private fun setObservers() {
        viewModel.moviesListLiveData.observe(requireActivity(), { resource ->
            resource?.let { list ->
                moviesAdapter.setItems(list)
            }
        })

        viewModel.creditListLiveData.observe(requireActivity(), { resource ->
            resource?.let { list ->
                castAdapter.setItems(list)
            }
        })
    }

    override fun onItemClicked(view: View?, item: Any) {
        (item as MovieCastItem).let { movieItem ->
            if (!movieItem.isMovie) {
                val directions = DetailsFragmentDirections.toPeople(
                    movieItem.movieId?.toInt()!!, movieItem.url!!
                )
                findNavController().navigate(directions)
            }
        }
    }
}