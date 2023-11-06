package com.example.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.MoviesEntity
import com.example.movieapp.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(private val repository: DatabaseRepository): ViewModel() {

    val favoriteMovieList = MutableLiveData<MutableList<MoviesEntity>>()
    val emptyList = MutableLiveData<Boolean>()

    fun loadFavoriteMovieList() = viewModelScope.launch {
        val list = repository.getAllFavoriteList()
        if (list.isNotEmpty()) {
            favoriteMovieList.postValue(list)
            emptyList.postValue(false)
        }else{
            emptyList.postValue(true)
        }
    }

}