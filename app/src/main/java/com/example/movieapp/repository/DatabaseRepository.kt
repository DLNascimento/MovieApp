package com.example.movieapp.repository

import com.example.movieapp.data.local.MoviesDao
import com.example.movieapp.data.local.MoviesEntity
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val dao: MoviesDao) {

    fun getAllFavoriteList() = dao.getAllMovies()
    suspend fun insertMovie(entity: MoviesEntity) = dao.insertMovie(entity)
    suspend fun deleteMovie(entity: MoviesEntity) = dao.deleteMovie(entity)
    suspend fun existMovie(id: Int) = dao.existsMovie(id)


}