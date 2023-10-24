package com.example.movieapp.data.dto

import com.example.movieapp.model.MovieModel
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("results")
    var resultados: List<MovieModel>
)