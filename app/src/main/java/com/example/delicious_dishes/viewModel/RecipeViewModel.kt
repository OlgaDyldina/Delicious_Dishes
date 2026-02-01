package com.example.delicious_dishes.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.delicious_dishes.adapter.RecipeInteractionListener
import com.example.delicious_dishes.dto.Category
import com.example.delicious_dishes.dto.RecipeDto
import com.example.delicious_dishes.model.MainDb
import com.example.delicious_dishes.repository.RecipeRepository
import com.example.delicious_dishes.repository.RoomRecipeRepositoryImpl
import com.example.delicious_dishes.util.SingleLiveEvent

class RecipeViewModel(
    application: Application
) : AndroidViewModel(application), RecipeInteractionListener {

    private val repository: RecipeRepository = RoomRecipeRepositoryImpl(
        dao = MainDb.getInstance(context = application).recipeDao
    )

    private var categoriesFilter: List<Category> = Category.values().toList()

    var setCategoryFilter = false

    val data = repository.data.map { list ->
        list.filter { categoriesFilter.contains(it.category) }
    }

    val separateRecipeViewEvent = SingleLiveEvent<Long>()

    val navigateRecipe = SingleLiveEvent<RecipeDto?>()
    val currentRecipe = MutableLiveData<RecipeDto?>(null)
    var favoriteFilter: MutableLiveData<Boolean> = MutableLiveData()

    init {
        favoriteFilter.value = false
    }

    fun showRecipesCategories(categories: List<Category>) {
        categoriesFilter = categories
        repository.update()
    }

    fun onSaveButtonClicked(recipe: RecipeDto) {
        if(recipe.content.isBlank() && recipe.name.isBlank()) return
        val newRecipe = currentRecipe.value?.copy(
            content = recipe.content,
            name = recipe.name,
            author = recipe.author,
            category = recipe.category
        ) ?: RecipeDto(
            id = RecipeRepository.NEW_RECIPE_ID,
            name = recipe.name,
            author = recipe.author,
            category = recipe.category,
            content = recipe.content
        )
        repository.save(newRecipe)
        currentRecipe.value = null
    }

    fun onAddButtonClicked() {
        navigateRecipe.call()
    }

    fun searchRecipeByName(recipeName: String) = repository.search(recipeName)

    override fun onRemoveButtonClicked(recipe: RecipeDto) = repository.delete(recipe.id)

    override fun onEditButtonClicked(recipe: RecipeDto) {
        currentRecipe.value = recipe
        navigateRecipe.value = recipe
    }

    override fun onRecipeCardClicked(recipe: RecipeDto) {
        separateRecipeViewEvent.value = recipe.id
    }

    override fun onFavouritesButtonClicked(recipe: RecipeDto) = repository.addToFavourites(recipe.id)

    override fun onRecipeItemClicked(recipe: RecipeDto) {
        navigateRecipe.value = recipe
    }
}