package com.example.delicious_dishes.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.internal.synchronized

@Database(
    entities = [RecipeEntity::class],
    version = 1
)
abstract class MainDb : RoomDatabase() {
    abstract val recipeDao: RecipeDao

    companion object {
        @Volatile
        private var instance: MainDb? = null

        fun getInstance(context: Context): MainDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                    .also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context, MainDb::class.java, "appRecipe.db"
            ).allowMainThreadQueries() // main thread
                .build()
    }

    annotation class Volatile
}