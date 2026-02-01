package com.example.delicious_dishes.model

import com.example.delicious_dishes.dto.RecipeDto

fun RecipeEntity.toModel() = RecipeDto(

    id = id,
    name = name,
    author = author,
    content = content,
    category = category,
    addToFavourites = addToFavourites,
    foodImage = foodImage
)

fun RecipeDto.toEntity() = RecipeEntity(
    id = id,
    name = name,
    author = author,
    content = content,
    category = category,
    addToFavourites = addToFavourites,
    foodImage = foodImage
)