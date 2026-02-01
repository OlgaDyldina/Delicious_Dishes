package com.example.delicious_dishes.repository

import androidx.lifecycle.MutableLiveData
import com.example.delicious_dishes.dto.Category
import com.example.delicious_dishes.dto.RecipeDto

object InMemoryRecipe : RecipeRepository {
    private var nextId = 1L

    private var recipes = listOf(
        RecipeDto(
            id = nextId++,
            name = "Классические пельмени",
            author = "Людмила Владимировна",
            category = Category.Russian,
            content = "1. Почистить и помыть лук, нарезать на четыре части.\n" +
                    "\n" +
                    "2. Мясо помыть, осушить нарезать на кубики.\n" +
                    "\n" +
                    "3. Пропустить мясо с луком через мясорубку. Вскипятить чайник.\n" +
                    "\n" +
                    "4. Посолить фарш и поперчить, добавить воды и хорошо перемешать. Фарш готов.\n" +
                    "\n" +
                    "5. Просеять муку через сито. Сделать тесто, для этого в миску положить 1 ч. ложку соли, налить стакан кипятка и, подсыпая муку столовой ложкой, быстро замесить тесто.\n" +
                    "\n" +
                    "6. Насыпать на стол муку, хорошо вымесить тесто.\n" +
                    "\n" +
                    "7. Скатать из теста 4 колбаски и нарезать их на маленькие кусочки. Получается примерно по 15 кусочков. 8. Кусочки теста примять и раскатать скалкой кружочки.\n" +
                    "\n" +
                    "9. На каждый кружочек выложить 1 ч. л фарша, сложить пельмень вдвое и тщательно защипить края. Так слепить все пельмени.\n" +
                    "\n" +
                    "10. Сварить пельмени, для этого вскипятить 3л воды в казане,воду посолить. Кинуть одну часть пельменей. Помешивать пельмени, пока они не всплывут. После этого варить 5 минут.\n" +
                    "\n" +
                    "11. Выложить пельмени в тарелку, положить кусочек сливочного масла, поперчить. Можно взбрызнуть уксусом. Классические пельмени готовы, можно подавать. А кто любит и со сметаной.\n" +
                    "\n" +
                    "Приятного аппетита!",
            addToFavourites = false
        ),
        RecipeDto(
            id = nextId++,
            name = "Том Ям",
            author = "Автор: Kulinarista",
            category = Category.Asian,
            content = "1. Готовим бульон без соли (или используем 1 бульонный кубик на 0,5 литра воды).\n" +
                    "\n" +
                    "2. Добавляем приправы: стебли лемонграсса (или цедру лайма), нарезанный галангал, листья каффира. Провариваем все 3-5 минут и вылавливаем из бульона.\n" +
                    "\n" +
                    "3. В кипящий бульон добавляем пасту Том Ям, очищенные креветки и нарезанные на крупные кусочки грибы. Провариваем при небольшом кипении около 5 минут.\n" +
                    "\n" +
                    "4. Добавляем рыбный соус, нарезанный колечками чили, сок лайма и порезанный кубиками помидор без шкурки.\n" +
                    "\n" +
                    "5. Вливаем кокосовое молоко. Варим после закипания 2-3 минуты.\n" +
                    "\n" +
                    "6. Осталось посыпать кинзой или зеленым луком — и суп Том Ям готов!",
            addToFavourites = false
        )
    )

    override val data = MutableLiveData(recipes)

    override fun save(recipe: RecipeDto) {
        if(recipe.id == RecipeRepository.NEW_RECIPE_ID) insert(recipe) else update(recipe)
    }

    override fun delete(recipeId: Long) {
        recipes = recipes.filter { it.id != recipeId }
        data.value = recipes
    }

    override fun addToFavourites(recipeId: Long) {
        recipes = recipes.map {
            if (it.id == recipeId)
                it.copy(addToFavourites = !it.addToFavourites)
            else it
        }
        data.value = recipes
    }

    override fun search(recipeName: String) {
        recipes.find {
            it.name == recipeName
        } ?: throw RuntimeException("Ничего не найдено")
        data.value = recipes
    }

    override fun getCategory(category: Category) {
        recipes.find {
            it.category == category
        }
        data.value = recipes
    }

    override fun update() {
        data.value = recipes
    }

    private fun update(recipe: RecipeDto) {
        recipes = recipes.map {
            if(it.id == recipe.id)
                recipe
            else
                it
        }
        data.value = recipes
    }

    private fun insert(recipe: RecipeDto) {
        recipes = listOf(
            recipe.copy(
                id = nextId++
            )
        ) + recipes
        data.value = recipes
    }
}