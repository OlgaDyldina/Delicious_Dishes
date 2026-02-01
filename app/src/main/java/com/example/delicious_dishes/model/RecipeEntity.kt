package com.example.delicious_dishes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.delicious_dishes.dto.Category

@Entity(tableName = "recipes")
class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "category")
    val category: Category,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "addToFavourites")
    val addToFavourites: Boolean,
    @ColumnInfo(name = "foodImage")
    val foodImage: String = ""
)