package com.example.delicious_dishes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.delicious_dishes.R
import com.example.delicious_dishes.adapter.showCategories
import com.example.delicious_dishes.databinding.NewRecipeFragmentBinding
import com.example.delicious_dishes.dto.Category
import com.example.delicious_dishes.dto.RecipeDto
import com.example.delicious_dishes.repository.RecipeRepository
import com.example.delicious_dishes.viewModel.RecipeViewModel


class NewOrEditedRecipeFragment : Fragment() {

    private val args by navArgs<NewOrEditedRecipeFragmentArgs>()

    private val newOrEditedRecipeViewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = NewRecipeFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        val thisRecipe = args.currentRecipe
        if(thisRecipe != null) {
            with(binding) {
                name.setText(thisRecipe.name)
                content.setText(thisRecipe.content)
                categoryRecipeCheckBox.check(R.id.checkBoxEuropean) // по умолчанию ставится
                checkBoxEuropean.text = checkBoxEuropean.context.showCategories(Category.European)
                checkBoxAsian.text = checkBoxAsian.context.showCategories(Category.Asian)
                checkBoxEastern.text = checkBoxEastern.context.showCategories(Category.Eastern)
                checkBoxRussian.text = checkBoxRussian.context.showCategories(Category.Russian)
                checkBoxAmerican.text = checkBoxAmerican.context.showCategories(Category.American)
            }
        }

        binding.name.requestFocus()

        binding.categoryRecipeCheckBox.setOnCheckedChangeListener {_, _ ->
            getCheckedCategory(binding.categoryRecipeCheckBox.checkedRadioButtonId)
        }
        binding.ok.setOnClickListener {
            onOkButtonClicked(binding)
        }
    }.root

    private fun onOkButtonClicked(binding: NewRecipeFragmentBinding) {
        val currentRecipe = RecipeDto(
            id = args.currentRecipe?.id ?: RecipeRepository.NEW_RECIPE_ID,
            name = binding.name.text.toString(),
            content = binding.content.text.toString(),
            author = binding.author.text.toString(),
            category = getCheckedCategory(binding.categoryRecipeCheckBox.checkedRadioButtonId)
        )
        if(emptyFieldsCheck(recipe = currentRecipe)) {
            val resultBundle = Bundle(1)
            resultBundle.putParcelable(RESULT_KEY, currentRecipe)
            setFragmentResult(REQUEST_KEY, resultBundle)
            findNavController().popBackStack()
        }
    }

    private fun getCheckedCategory(checkedId: Int) = when (checkedId) {
        R.id.checkBoxEuropean -> Category.European
        R.id.checkBoxAsian -> Category.Asian
        R.id.checkBoxEastern -> Category.Eastern
        R.id.checkBoxRussian -> Category.Russian
        R.id.checkBoxAmerican -> Category.American
        else -> throw IllegalArgumentException("Unknown type: $checkedId")
    }

    private fun emptyFieldsCheck(recipe: RecipeDto): Boolean {
        return if (recipe.name.isBlank() && recipe.content.isBlank() && recipe.author.isBlank()) {
            Toast.makeText(activity, "Заполните все поля", Toast.LENGTH_LONG).show()
            false
        } else true
    }

    companion object {
        const val REQUEST_KEY = "requestKey"
        const val RESULT_KEY = "newContent"
    }
}