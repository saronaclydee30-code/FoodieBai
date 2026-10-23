package com.foodiebai.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun FoodieBaiTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Green,
            onPrimary = Color.White,
            primaryContainer = Color(0xFFE5F1E8),
            onPrimaryContainer = Color(0xFF123B28),
            secondary = Color(0xFFE7774B),
            onSecondary = Color.White,
            background = Mist,
            onBackground = Color(0xFF17251D),
            surface = Color.White,
            onSurface = Color(0xFF17251D),
            surfaceVariant = Color(0xFFEDF2EE),
            onSurfaceVariant = HintGray,
            outline = Color(0xFFD5DED7),
            error = Color(0xFFB3261E)
        ),
        content = content
    )
}
