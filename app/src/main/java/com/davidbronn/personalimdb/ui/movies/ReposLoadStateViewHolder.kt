package com.davidbronn.personalimdb.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.LayoutMoviesBottomRowBinding
import com.davidbronn.personalimdb.utils.extensions.toVisibility

class ReposLoadStateViewHolder(
    private val binding: LayoutMoviesBottomRowBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.also {
            it.setOnClickListener { retry.invoke() }
        }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.tvNetworkIssue.text = loadState.error.localizedMessage
        }
        binding.progressBar.visibility = toVisibility(loadState is LoadState.Loading)
        binding.btnRetry.visibility = toVisibility(loadState !is LoadState.Loading)
        binding.tvNetworkIssue.visibility = toVisibility(loadState !is LoadState.Loading)
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): ReposLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_movies_bottom_row, parent, false)
            val binding = LayoutMoviesBottomRowBinding.bind(view)
            return ReposLoadStateViewHolder(
                binding,
                retry
            )
        }
    }
}
