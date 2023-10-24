package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("id")
    var id: String,
    @SerializedName("original_title")
    var title: String,
    @SerializedName("overview")
    var description: String,
    @SerializedName("poster_path")
    var poster_path: String,
    @SerializedName("release_date")
    var release_date: String,
)
