package com.example.delicious_dishes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.delicious_dishes.adapter.RecipesAdapter
import com.example.delicious_dishes.databinding.FavouriteFragmentBinding
import com.example.delicious_dishes.dto.RecipeDto
import com.example.delicious_dishes.viewModel.RecipeViewModel

class FavouriteRecipeFragment : Fragment() {

    private val favouriteRecipeViewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FavouriteFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        val adapter = RecipesAdapter(favouriteRecipeViewModel)
        binding.recipesRecycler.adapter = adapter

        favouriteRecipeViewModel.data.observe(viewLifecycleOwner) { recipes ->

            val favouriteRecipes = recipes.filter { it.addToFavourites }
            adapter.submitList(favouriteRecipes)

            val emptyList = recipes.none { it.addToFavourites }
            binding.textEmptyList.visibility =
                if(emptyList) View.VISIBLE else View.GONE
            binding.iconEmptyList.visibility =
                if(emptyList) View.VISIBLE else View.GONE
        }

        favouriteRecipeViewModel.separateRecipeViewEvent.observe(viewLifecycleOwner) { recipeCardId ->
            val direction =
                FavouriteRecipeFragmentDirections.actionFavouriteRecipeFragmentToSeparateRecipeFragment(
                    recipeCardId
                )
            findNavController().navigate(direction)
        }

        favouriteRecipeViewModel.navigateRecipe.observe(viewLifecycleOwner) { recipe ->
            val direction =
                FavouriteRecipeFragmentDirections.actionFavouriteRecipeFragmentToNewOrEditedRecipeFragment(
                    recipe
                )
            findNavController().navigate(direction)
        }
    }.root

    override fun onResume() {
        super.onResume()

        setFragmentResultListener(
            requestKey = NewOrEditedRecipeFragment.REQUEST_KEY
        ) {requestKey, bundle ->
            if(requestKey != NewOrEditedRecipeFragment.REQUEST_KEY) return@setFragmentResultListener
            val newRecipe = bundle.getParcelable<RecipeDto>(
                NewOrEditedRecipeFragment.RESULT_KEY
            ) ?: return@setFragmentResultListener
            favouriteRecipeViewModel.onSaveButtonClicked(newRecipe)
        }
    }
}