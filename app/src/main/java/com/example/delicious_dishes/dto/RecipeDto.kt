package com.example.delicious_dishes.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize

data class RecipeDto(
    val id: Long,
    val name: String,
    val author: String = "",
    val category: Category,
    val content: String,
    val addToFavourites: Boolean = false,
    val foodImage: String = ""

    ) : Parcelable

    @Serializable
    @Parcelize
    enum class Category: Parcelable {
        European,
        Asian,
        Eastern,
        Russian,
        American
    }
