package com.example.movieapp.repository

import com.example.movieapp.data.remote.ServiceApi
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: ServiceApi) {

    suspend fun getMovies(apiKey: String) = api.getPopularMovies(apiKey)
}