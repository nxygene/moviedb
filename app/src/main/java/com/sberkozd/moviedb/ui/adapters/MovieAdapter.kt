package com.sberkozd.moviedb.ui.adapters

import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sberkozd.moviedb.R
import com.sberkozd.moviedb.data.models.Movie
import com.sberkozd.moviedb.databinding.MovieListBinding
import com.sberkozd.moviedb.ui.catalog.CatalogFragmentDirections
import com.skydoves.bindables.binding

class MovieAdapter() : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val items: MutableList<Movie> = mutableListOf()

    var onItemClick: ((Movie) -> Unit)? = null

    var listener: MovieSelectionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = parent.binding<MovieListBinding>(R.layout.movie_list)
        return MovieViewHolder(binding).apply {
            binding.root.setOnClickListener {

                val position =
                    bindingAdapterPosition.takeIf { pos -> pos != RecyclerView.NO_POSITION }
                        ?: return@setOnClickListener
                listener?.onMovieSelected()
                it.findNavController().navigate(
                    CatalogFragmentDirections.actionSearchFragmentToDetailFragment(items[position])
                )
            }
        }
    }

    fun setMovieList(movieList: List<Movie>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(movieList)
        if (previousItemSize == 0) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeInserted(previousItemSize, movieList.size)
        }
    }

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

    fun getItemAt(position: Int): Movie {
        return items[position]
    }

    fun setMovieSelectionListener(listener: MovieSelectionListener) {
        this.listener = listener
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.apply {
            movie = items[position]
            executePendingBindings()
        }
    }

    override fun getItemCount() = items.size

    class MovieViewHolder(val binding: MovieListBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface MovieSelectionListener {
        fun onMovieSelected();
    }
}

