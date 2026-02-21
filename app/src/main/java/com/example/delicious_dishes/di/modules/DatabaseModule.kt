package com.example.delicious_dishes.di.modules

import android.content.Context
import androidx.room.Room
import com.example.delicious_dishes.repository.MainRepository
import com.example.delicious_dishes.bd.AppDatabase
import com.example.delicious_dishes.dao.RecipeDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideRecipeDao(context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "film_db"
        ).build().recipeDao()

    @Provides
    @Singleton
    fun provideRepository(recipeDao: RecipeDao) = MainRepository(recipeDao)
}