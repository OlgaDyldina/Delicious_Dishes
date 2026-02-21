package com.example.delicious_dishes.entity

import com.google.gson.annotations.SerializedName

data class TmdbResults (
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val tmdbRecipe: List<TmdbRecipe>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
