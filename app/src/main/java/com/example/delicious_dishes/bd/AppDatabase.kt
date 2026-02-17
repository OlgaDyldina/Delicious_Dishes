package com.example.delicious_dishes.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.delicious_dishes.dao.RecipeDao
import com.example.delicious_dishes.entity.Recipe

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}