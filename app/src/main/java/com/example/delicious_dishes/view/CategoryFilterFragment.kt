package com.example.delicious_dishes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.delicious_dishes.R
import com.example.delicious_dishes.databinding.CategoryFiltersBinding
import com.example.delicious_dishes.util.AnimationHelper
import com.example.delicious_dishes.viewmodel.SettingsFragmentViewModel
import io.reactivex.Observer


class CategoryFilterFragment :  Fragment() {
    private lateinit var binding: CategoryFiltersBinding
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(SettingsFragmentViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CategoryFiltersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(binding.filtersCategoryRoot, requireActivity(), 4)
        viewModel.categoryPropertyLifeData.observe(viewLifecycleOwner, Observer<String> {
            when(it) {
                CHECK_BOX_EUROPEAN -> binding.radioGroup.check(R.id.сheckBoxEuropean)
                CHECK_BOX_ASIAN -> binding.radioGroup.check(R.id.checkBoxAsian)
                CHECK_BOX_EASTERN -> binding.radioGroup.check(R.id.checkBoxEastern)
                CHECK_BOX_RUSSIAN -> binding.radioGroup.check(R.id.checkBoxRussian)
                CHECK_BOX_AMERICAN-> binding.radioGroup.check(R.id.checkBoxAmerican)
            }
        })
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.сheckBoxEuropean -> viewModel.putCategoryProperty(CHECK_BOX_EUROPEAN)
                R.id.checkBoxAsian -> viewModel.putCategoryProperty(CHECK_BOX_ASIAN)
                R.id.checkBoxEastern -> viewModel.putCategoryProperty(CHECK_BOX_EASTERN)
                R.id.checkBoxRussian -> viewModel.putCategoryProperty(CHECK_BOX_RUSSIAN)
                R.id.checkBoxAmerican -> viewModel.putCategoryProperty(CHECK_BOX_AMERICAN)
            }
        }
    }

    companion object {
        private const val CHECK_BOX_EUROPEAN = "European"
        private const val CHECK_BOX_ASIAN = "Asian"
        private const val CHECK_BOX_EASTERN = "Eastern"
        private const val CHECK_BOX_RUSSIAN = "Russian"
        private const val CHECK_BOX_AMERICAN = "American"
    }
}