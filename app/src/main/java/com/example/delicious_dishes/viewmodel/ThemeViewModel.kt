package com.example.delicious_dishes.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class ThemeState {
    Light, Dark, System
}

class ThemeViewModel : ViewModel() {

    private val _themeState = MutableStateFlow(ThemeState.System)
    val themeState: StateFlow<ThemeState> = _themeState

    fun toggleTheme() {
        _themeState.value = when (_themeState.value) {
            ThemeState.Light -> ThemeState.Dark
            ThemeState.Dark -> ThemeState.System
            ThemeState.System -> ThemeState.Light
        }
    }

    fun setTheme(themeState: ThemeState) {
        _themeState.value = themeState
    }
}
