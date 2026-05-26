package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = NeonCrimson,
    onPrimary = BoneWhite,
    secondary = AshGrey,
    onSecondary = BoneWhite,
    tertiary = ForbiddenPurple,
    onTertiary = LightBone,
    background = ObsidianBlack,
    onBackground = BoneWhite,
    surface = DarkSteel,
    onSurface = BoneWhite,
    error = GoreRed
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Force dark theme for the gothic gladiator vibe!
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
