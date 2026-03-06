package com.example.livebeer.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colorScheme = lightColorScheme(
    primary = Color(0xFFFFE000),
    onPrimary = Color(0xFF07080D),
    background = Color.White,
    onBackground = Color(0xFF07080D),
    surface = Color.White,
    onSurface = Color(0xFF07080D)
)

@Composable
fun LiveBeerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}