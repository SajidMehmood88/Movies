package com.zattoo.movies.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zattoo.movies.databinding.ListItemMoviesBinding
import com.zattoo.movies.domain.DomainMovies

class HomeAdapter : ListAdapter<DomainMovies, HomeAdapter.ViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(private val binding: ListItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DomainMovies) {
            binding.movies = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemMoviesBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<DomainMovies>() {
    override fun areItemsTheSame(oldItem: DomainMovies, newItem: DomainMovies): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DomainMovies, newItem: DomainMovies): Boolean {
        return oldItem == newItem
    }
}