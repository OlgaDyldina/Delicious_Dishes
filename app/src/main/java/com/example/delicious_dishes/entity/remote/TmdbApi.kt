package com.example.delicious_dishes.entity.remote

import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("4/recipes/{category}")
    fun getRecipe(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<com.example.delicious_dishes.entity.TmdbResults>

    @GET("4/search/recipe")
    fun getRecipeFromSearch(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Observable<com.example.delicious_dishes.entity.TmdbResults>
}