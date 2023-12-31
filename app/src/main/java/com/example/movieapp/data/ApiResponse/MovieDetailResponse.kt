package com.example.movieapp.data.ApiResponse

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
@SerializedName("adult")
val adult: Boolean,
@SerializedName("backdrop_path")
val backdropPath: String,
@SerializedName("belongs_to_collection")
val belongsToCollection: Any,
@SerializedName("budget")
val budget: Int,
@SerializedName("genres")
val genres: List<Genre>,
@SerializedName("homepage")
val homepage: String,
@SerializedName("id")
val id: Int,
@SerializedName("imdb_id")
val imdbId: String,
@SerializedName("original_language")
val originalLanguage: String,
@SerializedName("original_title")
val originalTitle: String,
@SerializedName("overview")
val overview: String,
@SerializedName("popularity")
val popularity: Double,
@SerializedName("poster_path")
val posterPath: String,
@SerializedName("production_companies")
val productionCompanies: List<ProductionCompany>,
@SerializedName("release_date")
val releaseDate: String,
@SerializedName("revenue")
val revenue: Int,
@SerializedName("runtime")
val runtime: Int,
@SerializedName("status")
val status: String,
@SerializedName("tagline")
val tagline: String,
@SerializedName("title")
val title: String,
@SerializedName("video")
val video: Boolean,
@SerializedName("vote_average")
val voteAverage: Double,
@SerializedName("vote_count")
val voteCount: Int
) {
    data class Genre(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )

    data class ProductionCompany(
        @SerializedName("id")
        val id: Int,
        @SerializedName("logo_path")
        val logoPath: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("origin_country")
        val originCountry: String // US
    )

}
