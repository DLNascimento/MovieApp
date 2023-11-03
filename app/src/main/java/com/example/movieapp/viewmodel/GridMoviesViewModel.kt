package com.example.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movieapp.data.ApiResponse.MovieResponse
import com.example.movieapp.paging.MoviesPagingSource
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class GridMoviesViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {


    val movieList = Pager(PagingConfig(1)){
        MoviesPagingSource(repository)
    }.flow.cachedIn(viewModelScope)
}