package com.example.delicious_dishes.repository

import android.database.Observable
import com.example.delicious_dishes.entity.Recipe
import com.example.delicious_dishes.dao.RecipeDao

class MainRepository (private val recipeDao: RecipeDao) {

    fun putToDb(films: List<Recipe>) {
        recipeDao.insertAll(films)
    }

    fun getAllFromDB(): Observable<List<Recipe>> = recipeDao.getCachedRecipes()
}