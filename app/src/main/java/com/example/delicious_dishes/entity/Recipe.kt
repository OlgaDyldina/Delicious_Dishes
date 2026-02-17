package com.example.delicious_dishes.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cached_recipes", indices = [Index(value = ["title"], unique = true)])
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "query") val query: String,
    @ColumnInfo(name = "cuisine") val cuisine: String,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "includeIngredients") val includeIngredients: String,
    @ColumnInfo(name = "readyInMinutes") var readyInMinutes: Number = 0.0,
    var isInFavorites: Boolean = false
) : Parcelable