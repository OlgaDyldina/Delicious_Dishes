package com.example.delicious_dishes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.viewModels
import com.example.delicious_dishes.adapter.showCategories
import com.example.delicious_dishes.databinding.CategoryFiltersBinding
import com.example.delicious_dishes.dto.Category
import com.example.delicious_dishes.viewModel.RecipeViewModel

class CategoryFilterFragment : Fragment() {

    private val categoryFilterViewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = CategoryFiltersBinding.inflate(layoutInflater, container, false).also { binding ->

        with(binding) {
            checkBoxEuropean.text = checkBoxEuropean.context.showCategories(Category.European)
            checkBoxAsian.text = checkBoxAsian.context.showCategories(Category.Asian)
            checkBoxEastern.text = checkBoxEastern.context.showCategories(Category.Eastern)
            checkBoxRussian.text = checkBoxRussian.context.showCategories(Category.Russian)
            checkBoxAmerican.text = checkBoxAmerican.context.showCategories(Category.American)

            binding.ok.setOnClickListener {
                onOkButtonClicked(binding)
            }
        }
    }.root

    private fun onOkButtonClicked(binding: CategoryFiltersBinding) {

        val categoryList = arrayListOf<Category>()
        var checkedCount = 5
        val nothingIsChecked = 0

        if(binding.checkBoxEuropean.isChecked) {
            categoryList.add(Category.European)
            categoryFilterViewModel.setCategoryFilter = true
        } else {
            --checkedCount
        }

        if(binding.checkBoxAsian.isChecked) {
            categoryList.add(Category.Asian)
            categoryFilterViewModel.setCategoryFilter = true
        } else {
            --checkedCount
        }

        if(binding.checkBoxEastern.isChecked) {
            categoryList.add(Category.Eastern)
            categoryFilterViewModel.setCategoryFilter = true
        } else {
            --checkedCount
        }

        if(binding.checkBoxRussian.isChecked) {
            categoryList.add(Category.Russian)
            categoryFilterViewModel.setCategoryFilter = true
        } else {
            --checkedCount
        }

        if(binding.checkBoxAmerican.isChecked) {
            categoryList.add(Category.American)
            categoryFilterViewModel.setCategoryFilter = true
        } else {
            --checkedCount
        }

        if(checkedCount == nothingIsChecked) {
            Toast.makeText(activity, "Нельзя убрать все фильтры", Toast.LENGTH_LONG).show()
        } else {
            categoryFilterViewModel.showRecipesCategories(categoryList)
            val resultBundle = Bundle(1)
            resultBundle.putParcelableArrayList(CHECKBOX_KEY, categoryList)
            setFragmentResult(CHECKBOX_KEY, resultBundle)
            findNavController().popBackStack()
        }
    }

    companion object {
        const val CHECKBOX_KEY = "checkBoxContent"
    }
}