package com.example.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.ApiResponse.MovieResponse
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class GridMoviesViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {


    private val _listaMoviesPopular = MutableLiveData<ResourceState<MovieResponse>>(ResourceState.Load())
    val listaMoviesPopular : LiveData<ResourceState<MovieResponse>> = _listaMoviesPopular

    init {
        getMoviesPopular()
    }


    private fun getMoviesPopular() = viewModelScope.launch{
        try {
            val response = repository.getMovies(Constants.API_KEY)
            _listaMoviesPopular.postValue(handleGetMovieResponse(response))

        }catch(t: Throwable){
            Log.e("TAG", "ERRO AO CARREGAR FILMES")
        }

    }


    private fun handleGetMovieResponse(response: Response<MovieResponse>): ResourceState<MovieResponse>{
        if (response.isSuccessful){
            response.body().let {
                return ResourceState.Success(it)
            }
        }else{
            return ResourceState.Error(response.message())
        }
    }
}