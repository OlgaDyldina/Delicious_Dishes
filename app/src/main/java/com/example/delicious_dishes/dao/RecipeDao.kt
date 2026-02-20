package com.example.delicious_dishes.dao

import android.database.Observable
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.example.delicious_dishes.dto.Category
import com.example.delicious_dishes.entity.Recipe
import com.example.delicious_dishes.model.RecipeEntity

@Dao
interface RecipeDao {
    @Query("SELECT * FROM cached_recipes")
    fun getCachedRecipes(): Observable<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Recipe>)
}