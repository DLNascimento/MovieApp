package com.example.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.dto.MovieResponse
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class GridMoviesViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {


    private val _listaMoviesPopular = MutableLiveData<ResourceState<MovieResponse>>(ResourceState.Load())
    val listaMoviesPopular : LiveData<ResourceState<MovieResponse>> = _listaMoviesPopular

    fun getMoviesPopular(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getMovies(Constants.API_KEY)
                _listaMoviesPopular.value = callResponse(response)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    private fun callResponse(response: Response<MovieResponse>):ResourceState<MovieResponse>{

        if (response.isSuccessful){
            response.body().let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }


}