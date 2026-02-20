package com.example.delicious_dishes.viewholder

import android.view.View
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import com.example.delicious_dishes.databinding.RecipeBinding
import com.example.delicious_dishes.entity.Recipe

class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val recipeItemBinding = RecipeBinding.bind(itemView)
    private val title = recipeItemBinding.name
    private val poster = recipeItemBinding.image
    private val author = recipeItemBinding.author
    private val cuisine = recipeItemBinding.cuisine

    fun bind(recipe: Recipe) {
        title.text = recipe.query
        Glide.with(itemView)
            .load(com.example.delicious_dishes.entity.ApiConstants.IMAGES_URL + "w342" + recipe.image)
            .centerCrop()
            .into(poster)
        author.text = recipe.author
        cuisine.text = recipe.cuisine
    }
}