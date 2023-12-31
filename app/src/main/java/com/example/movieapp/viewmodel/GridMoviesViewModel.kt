package com.example.movieapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movieapp.data.ApiResponse.MovieDetailResponse
import com.example.movieapp.data.ApiResponse.MovieResponse
import com.example.movieapp.data.local.MoviesEntity
import com.example.movieapp.paging.MoviesPagingSource
import com.example.movieapp.repository.DatabaseRepository
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class GridMoviesViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val databaseRepository: DatabaseRepository)
    : ViewModel() {


    val loading = MutableLiveData<Boolean>()

    val movieList = Pager(PagingConfig(1)){
        MoviesPagingSource(repository)
    }.flow.cachedIn(viewModelScope)


    val detailMovie = MutableLiveData<MovieDetailResponse>()
    fun loadDetailMovie(id: Int) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.getMovieId(id)
        if (response.isSuccessful){
            detailMovie.postValue(response.body())
        }
        loading.postValue(false)
    }


    val isFavorite = MutableLiveData<Boolean>()
    suspend fun existMovie(id:Int)= withContext(viewModelScope.coroutineContext){
        databaseRepository.existMovie(id)
    }

    fun favoriteMovie(id:Int, entity: MoviesEntity)=viewModelScope.launch {
        val exists= databaseRepository.existMovie(id)
        if (exists){
            isFavorite.postValue(false)
            databaseRepository.deleteMovie(entity)
        }else{
            isFavorite.postValue(true)
            databaseRepository.insertMovie(entity)
        }
    }


}