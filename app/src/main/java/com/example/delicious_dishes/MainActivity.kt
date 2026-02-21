package com.example.delicious_dishes

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Surface
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import com.example.delicious_dishes.databinding.ActivityMainBinding
import com.example.delicious_dishes.entity.Recipe
import com.example.delicious_dishes.receivers.ConnectionChecker
import com.example.delicious_dishes.view.CategoryFilterFragment
import com.example.delicious_dishes.view.FavouriteRecipeFragment
import com.example.delicious_dishes.view.FeedFragment
import com.example.delicious_dishes.view.PrepareLaterFragment
import com.example.delicious_dishes.view.SeparateRecipeFragment
import com.example.delicious_dishes.view.theme.AppTheme
import com.example.delicious_dishes.view.theme.LocalThemeState
import com.example.delicious_dishes.view.theme.ThemeState
import com.example.delicious_dishes.viewmodel.ThemeViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var reciver: BroadcastReceiver
    private val viewModel: ThemeViewModel by viewModels()

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

        setContent {
            val currentThemeState by viewModel.themeState.collectAsState()
            val darkTheme = when (currentThemeState) {
                ThemeState.Dark -> true
                ThemeState.Light -> false
                ThemeState.System -> LocalConfiguration.current.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_YES
            }
            AppTheme(darkTheme = darkTheme) {
                CompositionLocalProvider(
                    LocalThemeState provides Pair(
                        currentThemeState,
                        viewModel::toggleTheme
                    )
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = colorScheme.background
                    ) {
                        MainScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
    @Composable
    fun MainScreen(viewModel: ThemeViewModel) {
        val currentThemeState by viewModel.themeState.collectAsState()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Текущая тема: ${currentThemeState.name}")
            Button(onClick = { viewModel.toggleTheme() }) {
                Text("Переключить тему")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        val dummyViewModel = remember {ThemeViewModel() }
        AppTheme {
            CompositionLocalProvider(LocalThemeState provides Pair(ThemeState.Light, dummyViewModel::toggleTheme)) {
                MainScreen(viewModel = dummyViewModel)
            }
        }
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
                R.id.isWatched -> {
                    val tag = "isWatched"
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
