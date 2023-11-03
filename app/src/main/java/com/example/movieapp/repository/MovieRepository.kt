package com.example.movieapp.repository

import com.example.movieapp.data.remote.ServiceApi
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: ServiceApi) {

    suspend fun getMovies(page: Int) = api.getPopularMovies(page)

    suspend fun getMovieId(id: Int) = api.getMovieId(id)
}