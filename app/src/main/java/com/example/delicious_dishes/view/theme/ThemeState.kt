package com.example.delicious_dishes.view.theme

import androidx.compose.runtime.staticCompositionLocalOf

enum class ThemeState {
    Light, Dark, System
}

val LocalThemeState = staticCompositionLocalOf {
    Pair(ThemeState.System, {})
}
