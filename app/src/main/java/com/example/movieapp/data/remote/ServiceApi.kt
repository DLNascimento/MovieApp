package com.example.movieapp.data.remote

import com.example.movieapp.data.ApiResponse.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET("movie/now_playing")
    suspend fun getMovies(@Query("api_key")apiKey: String): Response<MovieResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key")apiKey: String): Response<MovieResponse>
}