package com.davidbronn.personalimdb.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.LayoutMovieItemBinding
import com.davidbronn.personalimdb.models.network.ResultsItem

/**
 * Created by Jude on 13/January/2020
 */
class SearchMoviesAdapter : RecyclerView.Adapter<SearchMoviesAdapter.SearchMoviesViewHolder>() {

    private var movieResults = mutableListOf<ResultsItem?>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchMoviesViewHolder {
        val binding = DataBindingUtil.inflate<LayoutMovieItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_movie_item, parent, false
        )
        return SearchMoviesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieResults.size
    }

    override fun onBindViewHolder(holder: SearchMoviesViewHolder, position: Int) {
        val movieItem = movieResults[position]
        movieItem?.let { holder.bindItem(movieItem) }
        setAnimation(holder.binding.root)
    }

    fun addItems(movies: List<ResultsItem?>) {
        movieResults.clear()
        movieResults.addAll(movies)
        notifyDataSetChanged()
    }

    inner class SearchMoviesViewHolder(val binding: LayoutMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(movieItem: ResultsItem) {
            binding.item = movieItem
            binding.executePendingBindings()
        }

        fun clearAnimations() {
            binding.root.animation = null
        }
    }

    override fun onViewDetachedFromWindow(holder: SearchMoviesViewHolder) {
        holder.clearAnimations()
        super.onViewDetachedFromWindow(holder)
    }

    private fun setAnimation(view: View) {
        val animation =
            AnimationUtils.loadAnimation(view.context, R.anim.item_animation_slide_from_right)
        view.startAnimation(animation)
    }
}