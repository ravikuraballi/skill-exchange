package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SkillSwapLightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = Tertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    background = Surface,
    onBackground = OnSurface,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    surfaceContainer = SurfaceContainer,
    surfaceContainerLow = SurfaceContainerLow,
    surfaceContainerHigh = SurfaceContainerHigh,
    surfaceContainerLowest = SurfaceContainerLowest,
    outline = Outline,
    outlineVariant = OutlineVariant,
    error = Error,
    errorContainer = ErrorContainer,
    onError = OnError
)

private val SkillSwapDarkColorScheme = darkColorScheme(
    primary = PrimaryFixed,
    onPrimary = OnPrimaryFixedVariant,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = SecondaryFixed,
    onSecondary = OnSecondaryContainer,
    secondaryContainer = Secondary,
    onSecondaryContainer = SecondaryContainer,
    tertiary = TertiaryFixed,
    onTertiary = TertiaryContainer,
    background = Color(0xFF0B1C30),
    onBackground = Color(0xFFEAF1FF),
    surface = Color(0xFF132238),
    onSurface = Color(0xFFEAF1FF),
    surfaceVariant = Color(0xFF213145),
    onSurfaceVariant = Color(0xFFC7C4D8),
    surfaceContainer = Color(0xFF1B2C42),
    surfaceContainerLow = Color(0xFF15263C),
    surfaceContainerHigh = Color(0xFF24364D),
    surfaceContainerLowest = Color(0xFF0E1A2B),
    outline = OutlineVariant,
    outlineVariant = Outline,
    error = Error,
    onError = OnError
)

@Composable
fun SkillSwapTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) SkillSwapDarkColorScheme else SkillSwapLightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
