package com.example.delicious_dishes.adapter

import com.example.delicious_dishes.dto.RecipeDto

interface RecipeInteractionListener {

    fun onRemoveButtonClicked(recipe: RecipeDto)
    fun onEditButtonClicked(recipe: RecipeDto)
    fun onRecipeCardClicked(recipe: RecipeDto)
    fun onFavouritesButtonClicked(recipe: RecipeDto)
    fun onRecipeItemClicked(recipe: RecipeDto)
}