package com.example.delicious_dishes.viewmodel


import android.database.Observable
import androidx.lifecycle.ViewModel
import com.example.delicious_dishes.App
import com.example.delicious_dishes.domain.Interactor
import com.example.delicious_dishes.entity.Recipe
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class RecipeViewModel : ViewModel() {

    @Inject
    lateinit var interactor: Interactor
    val recipesListData: Observable<List<Recipe>>
    val showProgressBar: BehaviorSubject<Boolean>

    init {
        App.instance.dagger.inject(this)
        showProgressBar = interactor.progressBarState
        recipesListData = interactor.getRecipesFromDB()
        getRecipes()
    }

    fun getRecipes() {
        interactor.getRecipesFromApi(1)
    }

    fun getSearchResult(search: String) = interactor.getSearchResultFromApi(search)

    companion object {
        fun doSearchPagination(
            visibleItemCount: Int,
            totalItemCount: Int,
            pastVisibleItemCount: Int,
            query: String
        ) {
            if (interactor.needLoading) {
                if ((visibleItemCount + pastVisibleItemCount) >= totalItemCount - 5) {
                    interactor.needLoading = false

                    val page = currentlyLoadedSearchPage++
                    if (page > totalPagersFromSearch) return

                    showProgressBarLiveData.postValue(true)
                    getDataFromSearch(query, page)
                }
            }
        }

    }
}