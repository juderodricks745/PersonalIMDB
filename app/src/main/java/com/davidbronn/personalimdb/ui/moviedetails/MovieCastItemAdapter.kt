package com.davidbronn.personalimdb.ui.moviedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.LayoutMovieCastItemBinding
import com.davidbronn.personalimdb.models.network.MovieCastItem

/**
 * Created by Jude on 13/January/2020
 */
class MovieCastItemAdapter : RecyclerView.Adapter<MovieCastItemAdapter.MovieCastItemViewHolder>() {

    private var movieCastList = mutableListOf<MovieCastItem>()

    override fun getItemCount(): Int {
        return movieCastList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCastItemViewHolder {
        val binding = DataBindingUtil.inflate<LayoutMovieCastItemBinding>(LayoutInflater.from(parent.context), R.layout.layout_movie_cast_item, parent, false)
        return MovieCastItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieCastItemViewHolder, position: Int) {
        val movieCastItem = movieCastList[position]
        holder.bindMovieCastItem(movieCastItem)
    }

    fun setItems(items: List<MovieCastItem>) {
        movieCastList.addAll(items)
        notifyDataSetChanged()
    }

    inner class MovieCastItemViewHolder(val binding: LayoutMovieCastItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindMovieCastItem(movieCastItem: MovieCastItem) {
            binding.item = movieCastItem
            binding.executePendingBindings()
        }
    }
}