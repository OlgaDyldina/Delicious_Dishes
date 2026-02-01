package com.example.delicious_dishes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.delicious_dishes.dto.RecipeDto
import java.util.Locale

class RecipesAdapter(
    private val interactionListener: RecipeInteractionListener
) : ListAdapter<RecipeDto, RecipesAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    class ViewHolder(
        private val binding: RecipeBinding,
        listener: RecipeInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var recipe: RecipeDto

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveButtonClicked(recipe)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditButtonClicked(recipe)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.options.setOnClickListener { popupMenu.show() }
        }

        init {
            binding.authorName.setOnClickListener { listener.onRecipeCardClicked(recipe) }
            binding.name.setOnClickListener { listener.onRecipeCardClicked(recipe) }
            binding.avatar.setOnClickListener { listener.onRecipeCardClicked(recipe) }
        }

        init {
            itemView.setOnClickListener { listener.onRecipeItemClicked(recipe) }
            binding.favourite.setOnClickListener { listener.onFavouritesButtonClicked(recipe) }
        }

        fun bind(recipe: RecipeDto) {
            this.recipe = recipe

            with(binding) {
                name.text = recipe.name
                authorName.text = recipe.author
                category.text = category.context.showCategories(recipe.category)
                favourite.isChecked = recipe.addToFavourites
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<RecipeDto>() {
        override fun areItemsTheSame(oldItem: RecipeDto, newItem: RecipeDto) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: RecipeDto, newItem: RecipeDto) =
            oldItem == newItem
    }
}

fun Context.showCategories(category: Locale.Category) : String {
    return when (category) {
        Category.European -> getString(R.string.european_type)
        Category.Asian -> getString(R.string.asian_type)
        Category.Eastern -> getString(R.string.eastern_type)
        Category.Russian -> getString(R.string.russian_type)
        Category.American -> getString(R.string.american_type)
    }
}