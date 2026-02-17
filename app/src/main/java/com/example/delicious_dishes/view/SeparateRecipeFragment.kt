package com.example.delicious_dishes.view

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.delicious_dishes.R
import com.example.delicious_dishes.databinding.SeparateRecipeViewBinding
import com.example.delicious_dishes.entity.Recipe
import com.example.delicious_dishes.view.notifications.NotificationHelper
import com.example.delicious_dishes.viewmodel.DetailsFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.jar.Manifest

class SeparateRecipeFragment : Fragment() {
    private lateinit var recipe: Recipe
    private lateinit var binding: SeparateRecipeViewBinding
    private val viewModel: DetailsFragmentViewModel by viewModels()
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SeparateRecipeViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecipesDetails()

        binding.detailsFabFavorites.setOnClickListener {
            if (!recipe.isInFavorites) {
                binding.detailsFabFavorites.setImageResource(R.drawable.baseline_favorite_24)
                recipe.isInFavorites = true
            } else {
                binding.detailsFabFavorites.setImageResource(R.drawable.baseline_favorite_border_24)
                recipe.isInFavorites = false
            }
        }

        binding.detailsFab.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this recipe: ${recipe.query} \n\n ${recipe.includeIngredients}"
            )
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share To:"))
        }

        binding.detailsFabDownloadWp.setOnClickListener {
            performAsyncLoadOfPoster()
        }

        binding.detailsFabPrepareLater.setOnClickListener {
            NotificationHelper.notificationSet(requireContext(), recipe)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }


    private fun setRecipesDetails() {
        recipe = arguments?.get("film") as Recipe
        binding.detailsToolbar.title = recipe.query
        Glide.with(this)
            .load(com.example.delicious_dishes.entity.ApiConstants.IMAGES_URL + "w780" + recipe.image)
            .centerCrop()
            .into(binding.detailsImage)
        binding.detailsDescription.text = recipe.includeIngredients

        binding.detailsFabFavorites.setImageResource(
            if (recipe.isInFavorites) R.drawable.baseline_favorite_24
            else R.drawable.baseline_favorite_border_24
        )
    }

    private fun performAsyncLoadOfPoster() {
        if (!checkPermission()) {
            requestPermission()
            return
        }
        MainScope().launch {
            binding.progressBar.isVisible = true
            val job = scope.async {
                viewModel.loadWallpaper(com.example.delicious_dishes.entity.ApiConstants.IMAGES_URL + "original" + recipe.image)
            }
            saveToGallery(job.await())
            Snackbar.make(
                binding.root,
                R.string.recipe_save,
                Snackbar.LENGTH_LONG
            )
                .setAction(R.string.open) {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.type = "image/*"
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                .show()

            binding.progressBar.isVisible = false
        }
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
    }

    private fun saveToGallery(bitmap: Bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.TITLE, recipe.query.handleSingleQuote())
                put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    recipe.query.handleSingleQuote()
                )
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(
                    MediaStore.Images.Media.DATE_ADDED,
                    System.currentTimeMillis() / 1000
                )
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/DeliciousDishesApp")
            }
            val contentResolver = requireActivity().contentResolver
            val uri = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            val outPutStream = contentResolver.openOutputStream(uri!!)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outPutStream)
            outPutStream?.close()
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.insertImage(
                requireActivity().contentResolver,
                bitmap,
                recipe.image.handleSingleQuote(),
                recipe.includeIngredients.handleSingleQuote()
            )
        }
    }

    private fun String.handleSingleQuote(): String {
        return this.replace("'", "")
    }
}