package com.example.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieapp.data.ApiResponse.MovieResponse
import com.example.movieapp.data.ApiResponse.Result
import com.example.movieapp.databinding.PopularMovieItemBinding
import com.example.movieapp.utils.Constants

class GridMovieAdapter : PagingDataAdapter<Result,GridMovieAdapter.GridMovieViewHolder>(differCallBack) {


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

    override fun onBindViewHolder(holder: GridMovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {


            txtMovieTitle.text = currentItem?.title
            txtMovieDate.text = currentItem?.release_date

            imgPopularMovie.load("${Constants.BASE_URL_IMAGEM}${currentItem?.poster_path}"){
                crossfade(true)
            }
        }

        holder.binding.root.setOnClickListener {
            onItemClickListener.let {
                if (currentItem != null) {
                    if (it != null) {
                        it(currentItem)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (Result) -> Unit) {
        onItemClickListener = listener
    }

    companion object{

        private val differCallBack = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }

        }

    }

    inner class GridMovieViewHolder(val binding: PopularMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)


}