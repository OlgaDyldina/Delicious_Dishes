package com.example.delicious_dishes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.delicious_dishes.adapter.TopSpacingItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.delicious_dishes.MainActivity
import com.example.delicious_dishes.adapter.RecipesAdapter
import com.example.delicious_dishes.databinding.FavouriteFragmentBinding
import com.example.delicious_dishes.entity.Recipe
import com.example.delicious_dishes.util.AnimationHelper


class FavouriteRecipeFragment : Fragment() {
    private lateinit var binding: FavouriteFragmentBinding
    private lateinit var recipesAdapter: RecipesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavouriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favoritesList: List<Recipe> = emptyList()

        AnimationHelper.performFragmentCircularRevealAnimation(binding.favouriteFragment, requireActivity(),2)

        binding.recipesRecycler.apply {
            recipesAdapter = RecipesAdapter(object : RecipesAdapter.OnItemClickListener {
                override fun click(recipe: Recipe) {
                    (requireActivity() as MainActivity).launchDetailsFragment(recipe)
                }
            })
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
        recipesAdapter.addItems(favoritesList)
    }
}