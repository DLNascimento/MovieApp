package com.example.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieapp.databinding.PopularMovieItemBinding
import com.example.movieapp.model.MovieModel

class GridMovieAdapter : RecyclerView.Adapter<GridMovieAdapter.GridMovieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridMovieViewHolder {
        return GridMovieViewHolder(
            PopularMovieItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: GridMovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.binding.apply {
            imgPopularMovie.load(movie.poster_path){
                crossfade(true)
            }
        }
    }


    inner class GridMovieViewHolder(val binding: PopularMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.id == newItem.id && oldItem.title == newItem.title &&
                    oldItem.description == newItem.description &&
                    oldItem.release_date == newItem.release_date &&
                    oldItem.poster_path == newItem.poster_path
        }

    }



    private val differ = AsyncListDiffer(this, differCallBack)

    var movies: List<MovieModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)
}