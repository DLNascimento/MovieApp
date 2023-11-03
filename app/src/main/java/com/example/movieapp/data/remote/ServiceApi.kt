package com.example.movieapp.data.remote

import com.example.movieapp.data.ApiResponse.MovieDetailResponse
import com.example.movieapp.data.ApiResponse.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page")page: Int): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieId(@Path("movie_id")id: Int): Response<MovieDetailResponse>
}