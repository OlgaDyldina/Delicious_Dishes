package com.example.delicious_dishes.domain

import com.example.delicious_dishes.API
import com.example.delicious_dishes.repository.MainRepository
import com.example.delicious_dishes.entity.Recipe
import com.example.delicious_dishes.entity.remote.TmdbApi
import com.example.delicious_dishes.preference.PreferenceProvider
import com.example.delicious_dishes.util.Converter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.kotlin.subscribeBy


class Interactor (private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferences: PreferenceProvider) {
    var progressBarState = BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun getRecipesFromApi(page: Int) {
        progressBarState.onNext(true)

        retrofitService.getRecipe(getDefaultCategoryFromPreferences(), API.KEY, "ru-RU", page)
            .subscribeOn(Schedulers.io())
            .map {
                Converter.convertApiListToDTOList(it.tmdbRecipe)
            }
            .subscribeBy(
                onError = {
                    progressBarState.onNext(false)
                },
                onNext = {
                    progressBarState.onNext(false)
                    repo.putToDb(it)
                }
            )
    }

    fun getSearchResultFromApi(search: String): Observable<List<Recipe>> = retrofitService.getRecipeFromSearch((API.KEY), "ru-RU", search, 1)
        .map {
            Converter.convertApiListToDTOList(it.tmdbRecipe)
        }

    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }
    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()

    fun getRecipesFromDB(): android.database.Observable<List<Recipe>> = repo.getAllFromDB()
}