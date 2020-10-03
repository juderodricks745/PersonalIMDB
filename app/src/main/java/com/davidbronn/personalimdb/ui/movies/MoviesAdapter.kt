package com.davidbronn.personalimdb.ui.movies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.davidbronn.personalimdb.databinding.LayoutMovieItemBinding
import com.davidbronn.personalimdb.models.network.ResultsItem
import com.davidbronn.personalimdb.ui.interfaces.IRecyclerItemClickListener

/**
 * Created by Jude on 12/January/2020
 */
class MoviesAdapter :
    PagingDataAdapter<ResultsItem, MoviesAdapter.MoviesViewHolder>(ResultsItem.DIFF_CALLBACK) {

    private var itemClickListener: IRecyclerItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutMovieItemBinding.inflate(inflater, parent, false)
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movieItem = getItem(position)
        movieItem?.let { holder.bindItem(movieItem) }
        holder.binding.root.setOnClickListener(holder)
    }

    fun setItemClickListener(itemClickListener: IRecyclerItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    // region [ViewHolder]
    inner class MoviesViewHolder(val binding: LayoutMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        @SuppressLint("SyntheticAccessor")
        override fun onClick(v: View?) {
            this@MoviesAdapter.itemClickListener?.onItemClicked(
                binding.root,
                getItem(bindingAdapterPosition) as ResultsItem
            )
        }

        fun bindItem(movieItem: ResultsItem) {
            binding.item = movieItem
            binding.executePendingBindings()
        }
    }
    // endregion
}