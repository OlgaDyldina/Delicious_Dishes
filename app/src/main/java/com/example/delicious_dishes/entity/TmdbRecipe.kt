package com.example.delicious_dishes.entity

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.BinaryFormat

data class TmdbRecipe(
    @SerializedName("query")
    val query: String,
    @SerializedName("cuisine")
    val cuisine: String,
    @SerializedName("includeIngredients")
    val includeIngredients: String,
    @SerializedName("instructionsRequired")
    val instructionsRequired: Boolean,
    @SerializedName("maxReadyTime")
    val maxReadyTime: Number,
    @SerializedName("author")
    val author: String,
    @SerializedName("image")
    val image: BinaryFormat,
)