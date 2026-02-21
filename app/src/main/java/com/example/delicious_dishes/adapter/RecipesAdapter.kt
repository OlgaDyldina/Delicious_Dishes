package com.example.delicious_dishes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delicious_dishes.entity.Recipe
import com.example.delicious_dishes.viewholder.RecipeViewHolder
import com.example.delicious_dishes.viewmodel.RecipeViewModel
import com.example.delicious_dishes.R


class RecipesAdapter (private val clickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<Recipe>()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecipeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recipe, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecipeViewHolder -> {
                holder.bind(items[position])
                holder.itemView.setOnClickListener {
                    clickListener.click(items[position])
                }
            }
        }
    }

    fun addItems(list: List<Recipe>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun click(recipe: Recipe)
    }

    private fun RecyclerView.initSearchPagination() {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount = recyclerView.layoutManager!!.childCount
                    val totalItemCount = recyclerView.layoutManager!!.itemCount
                    val pastVisibleItemCount =
                        (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                    RecipeViewModel.doSearchPagination(
                        visibleItemCount,
                        totalItemCount,
                        pastVisibleItemCount,
                        query
                    )
                }
            }
        })
    }
}