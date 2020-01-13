package com.davidbronn.personalimdb.ui.movieslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.LayoutMovieItemBinding
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.utils.IRecyclerItemClickListener

/**
 * Created by Jude on 12/January/2020
 */
class MoviesAdapter :
    PagedListAdapter<ResultsItem, MoviesAdapter.MoviesViewHolder>(ResultsItem.DIFF_CALLBACK) {

    private var itemClickListener: IRecyclerItemClickListener? = null

    fun setItemClickListener(itemClickListener: IRecyclerItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = DataBindingUtil.inflate<LayoutMovieItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_movie_item, parent, false
        )
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movieItem = getItem(position)
        movieItem?.let { holder.bindItem(movieItem) }
        holder.binding.root.setOnClickListener(holder)
        setAnimation(holder.binding.root)
    }

    inner class MoviesViewHolder(val binding: LayoutMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        override fun onClick(v: View?) {
            itemClickListener?.onItemClicked(v, getItem(adapterPosition) as ResultsItem)
        }

        fun bindItem(movieItem: ResultsItem) {
            binding.item = movieItem
            binding.executePendingBindings()
        }

        fun clearAnimations() {
            binding.root.animation = null
        }
    }

    override fun onViewDetachedFromWindow(holder: MoviesViewHolder) {
        holder.clearAnimations()
        super.onViewDetachedFromWindow(holder)
    }

    private fun setAnimation(view: View) {
        val animation =
            AnimationUtils.loadAnimation(view.context, R.anim.item_animation_slide_from_right)
        view.startAnimation(animation)
    }
}