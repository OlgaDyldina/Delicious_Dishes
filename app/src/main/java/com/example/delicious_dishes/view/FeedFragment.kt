package com.example.delicious_dishes.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.delicious_dishes.MainActivity
import com.example.delicious_dishes.adapter.RecipesAdapter
import com.example.delicious_dishes.adapter.TopSpacingItemDecoration
import com.example.delicious_dishes.databinding.FeedFragmentBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import com.example.delicious_dishes.entity.Recipe
import com.example.delicious_dishes.util.AnimationHelper
import com.example.delicious_dishes.util.AutoDisposable
import com.example.delicious_dishes.util.addTo
import com.example.delicious_dishes.viewmodel.RecipeViewModel
import java.util.Locale
import java.util.concurrent.TimeUnit


class FeedFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(RecipeViewModel::class.java)
    }
    private val autoDisposable = AutoDisposable()
    private lateinit var binding: FeedFragmentBinding
    private lateinit var recipesAdapter: RecipesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        autoDisposable.bindTo(lifecycle)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FeedFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(
            binding.feedFragment,
            requireActivity(),
            1
        )

        initSearchView()
        initPullToRefresh()
        initRecyckler()

        viewModel.recipesListData
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                recipesAdapter.addItems(list)
            }
            .addTo(autoDisposable)
        viewModel.showProgressBar
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.progressBar.isVisible = it
            }
            .addTo(autoDisposable)
    }

    private fun initPullToRefresh() {
        binding.pullToRefresh.setOnRefreshListener {
            recipesAdapter.items.clear()
            viewModel.getRecipes()
            binding.pullToRefresh.isRefreshing = false
        }
    }
    private fun initSearchView() {
        binding.search.setOnClickListener {
            binding.search.isIconified = false
        }

        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            binding.search.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    recipesAdapter.items.clear()
                    subscriber.onNext(newText)
                    return false
                }
                override fun onQueryTextSubmit(query: String): Boolean {
                    subscriber.onNext(query)
                    return false
                }
            })
        })
            .subscribeOn(Schedulers.io())
            .map {
                it.toLowerCase(Locale.getDefault()).trim()
            }
            .debounce(800, TimeUnit.MILLISECONDS)
            .filter {
                viewModel.getRecipes()
                it.isNotBlank()
            }
            .flatMap {
                viewModel.getSearchResult(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = {
                    Toast.makeText(requireContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show()
                },
                onNext = {
                    recipesAdapter.addItems(it)
                }
            )
            .addTo(autoDisposable)
    }

    private fun initRecyckler() {
        binding.recipesRecyclerView.apply {
            recipesAdapter =
                RecipesAdapter(object : RecipesAdapter.OnItemClickListener {
                    override fun click(recipe: Recipe) {
                        (requireActivity() as MainActivity).launchDetailsFragment(recipe)
                    }
                })
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
    }

}
