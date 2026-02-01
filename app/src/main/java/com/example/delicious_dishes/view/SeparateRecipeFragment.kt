package com.example.delicious_dishes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.delicious_dishes.R
import com.example.delicious_dishes.adapter.RecipesAdapter
import com.example.delicious_dishes.databinding.SeparateRecipeViewBinding
import com.example.delicious_dishes.dto.RecipeDto
import com.example.delicious_dishes.viewModel.RecipeViewModel

class SeparateRecipeFragment : Fragment() {

    private val args by navArgs<SeparateRecipeFragmentArgs>()

    private val separateRecipeViewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = SeparateRecipeViewBinding.inflate(layoutInflater, container, false).also { binding ->
        val viewHolder =
            RecipesAdapter.ViewHolder(binding.separateRecipeView, separateRecipeViewModel)
        separateRecipeViewModel.data.observe(viewLifecycleOwner) { recipes ->
            val separatedRecipe = recipes.find { it.id == args.recipeCardId } ?: run {
                findNavController().navigateUp()
                return@observe
            }
            viewHolder.bind(separatedRecipe)
            binding.content.text = separatedRecipe.content
            binding.recipeImage.setImageResource(R.drawable.food_image)
            binding.recipeImage.visibility =
                if(separatedRecipe.foodImage.isBlank()) View.GONE else View.VISIBLE
        }

        separateRecipeViewModel.navigateRecipe.observe(viewLifecycleOwner) { recipe ->
            val direction =
                SeparateRecipeFragmentDirections.actionSeparateRecipeFragmentToNewOrEditedRecipeFragment(
                    recipe
                )
            findNavController().navigate(direction)
        }

        setFragmentResultListener(
            requestKey = NewOrEditedRecipeFragment.REQUEST_KEY
        ) {requestKey, bundle ->
            if(requestKey != NewOrEditedRecipeFragment.REQUEST_KEY) return@setFragmentResultListener
            val newRecipe = bundle.getParcelable<RecipeDto>(
                NewOrEditedRecipeFragment.RESULT_KEY
            ) ?: return@setFragmentResultListener
            separateRecipeViewModel.onSaveButtonClicked(newRecipe)
        }
    }.root
}