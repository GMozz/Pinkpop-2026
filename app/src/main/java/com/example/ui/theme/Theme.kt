package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PinkpopPink,
    secondary = PinkpopHighlightBg,
    tertiary = PinkpopWhite,
    background = PinkpopDarkBg,
    surface = PinkpopWhite,
    onPrimary = PinkpopWhite,
    onSecondary = PinkpopHighlightText,
    onBackground = PinkpopCardText,
    onSurface = PinkpopCardText
)

private val LightColorScheme = lightColorScheme(
    primary = PinkpopPink,
    secondary = PinkpopHighlightBg,
    tertiary = PinkpopWhite,
    background = PinkpopDarkBg,
    surface = PinkpopWhite,
    onPrimary = PinkpopWhite,
    onSecondary = PinkpopHighlightText,
    onBackground = PinkpopCardText,
    onSurface = PinkpopCardText
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // We disable dynamicColor by default to guarantee the signature Pinkpop branding
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
