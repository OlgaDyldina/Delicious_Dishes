package com.example.delicious_dishes

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.delicious_dishes.databinding.ActivityMainBinding
import com.example.delicious_dishes.entity.Recipe
import com.example.delicious_dishes.receivers.ConnectionChecker
import com.example.delicious_dishes.view.CategoryFilterFragment
import com.example.delicious_dishes.view.FavouriteRecipeFragment
import com.example.delicious_dishes.view.FeedFragment
import com.example.delicious_dishes.view.PrepareLaterFragment
import com.example.delicious_dishes.view.SeparateRecipeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var reciver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.nav_host_fragment, FeedFragment())
            .addToBackStack(null)
            .commit()

        reciver = ConnectionChecker()
        val filters = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_BATTERY_LOW)
        }
        registerReceiver(reciver, filters)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(reciver)
    }


    fun launchDetailsFragment(recipe: Recipe) {

        val bundle = Bundle()
        bundle.putParcelable("recipe", recipe)
        val fragment = SeparateRecipeFragment()
        fragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun initNavigation() {

        binding.bottomNavigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.feed -> {
                    val tag = "home"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: FeedFragment(), tag)
                    true
                }

                R.id.favorites -> {
                    val tag = "favorites"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: FavouriteRecipeFragment(), tag)
                    true
                }

                R.id.prepare_later -> {
                    val tag = "prepare later"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: PrepareLaterFragment(), tag)
                    true
                }

                R.id.categoryFilter -> {
                    val tag = "selections"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: CategoryFilterFragment(), tag)
                    true
                }

                else -> false
            }
        }
    }

    private fun checkFragmentExistence(tag: String): Fragment? =
        supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, fragment, tag)
            .addToBackStack(null)
            .commit()
    }
}