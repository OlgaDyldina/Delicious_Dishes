package com.example.delicious_dishes.util

import com.example.delicious_dishes.entity.Recipe
import com.example.delicious_dishes.entity.TmdbRecipe

object Converter {
    fun convertApiListToDTOList(list: List<TmdbRecipe>?): List<Recipe> {
        val result = mutableListOf<Recipe>()
        list?.forEach {
            result.add(Recipe(
                query = it.query,
                image = it.image.toString(),
                includeIngredients = it.includeIngredients,
                author = it.author,
                cuisine = it.cuisine,
                isInFavorites = false
            ))
        }
        return result
    }
}