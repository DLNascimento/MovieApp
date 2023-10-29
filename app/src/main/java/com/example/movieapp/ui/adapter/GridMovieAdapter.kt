package com.example.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieapp.data.ApiResponse.MovieResponse
import com.example.movieapp.data.ApiResponse.Result
import com.example.movieapp.databinding.PopularMovieItemBinding
import com.example.movieapp.utils.Constants

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
            imgPopularMovie.load("${Constants.BASE_URL_IMAGEM}${movie.poster_path}"){
                crossfade(true)
            }
        }
    }


    inner class GridMovieViewHolder(val binding: PopularMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }


    }



    private val differ = AsyncListDiffer(this, differCallBack)


    var movies: List<Result>
        get() = differ.currentList
        set(value) = differ.submitList(value)
}