package com.example.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

// SkillSwap Brand Palette (as defined in reference design)
val Primary = Color(0xFF3525CD)
val OnPrimary = Color(0xFFFFFFFF)
val PrimaryContainer = Color(0xFF4F46E5)
val OnPrimaryContainer = Color(0xFFDAD7FF)
val PrimaryFixed = Color(0xFFE2DFFF)
val OnPrimaryFixedVariant = Color(0xFF3323CC)

val Secondary = Color(0xFF006C4A)
val OnSecondary = Color(0xFFFFFFFF)
val SecondaryContainer = Color(0xFF82F5C1)
val OnSecondaryContainer = Color(0xFF00714E)
val SecondaryFixed = Color(0xFF85F8C4)
val SecondaryFixedDim = Color(0xFF68DBA9)
val OnSecondaryFixed = Color(0xFF002114)

val Tertiary = Color(0xFF5C00CA)
val OnTertiary = Color(0xFFFFFFFF)
val TertiaryContainer = Color(0xFF7531E6)
val OnTertiaryContainer = Color(0xFFE4D4FF)
val TertiaryFixed = Color(0xFFEADDFF)
val TertiaryFixedDim = Color(0xFFD2BBFF)
val OnTertiaryFixedVariant = Color(0xFF5A00C6)

val Surface = Color(0xFFF8F9FF)
val SurfaceContainerLowest = Color(0xFFFFFFFF)
val SurfaceContainerLow = Color(0xFFEFF4FF)
val SurfaceContainer = Color(0xFFE5EEFF)
val SurfaceContainerHigh = Color(0xFFDCE9FF)
val SurfaceVariant = Color(0xFFD3E4FE)
val OnSurface = Color(0xFF0B1C30)
val OnSurfaceVariant = Color(0xFF464555)

val Outline = Color(0xFF777587)
val OutlineVariant = Color(0xFFC7C4D8)

val Error = Color(0xFFBA1A1A)
val ErrorContainer = Color(0xFFFFDAD6)
val OnError = Color(0xFFFFFFFF)

val ColorScheme.primaryFixed: Color get() = PrimaryFixed
val ColorScheme.onPrimaryFixedVariant: Color get() = OnPrimaryFixedVariant
val ColorScheme.secondaryFixed: Color get() = SecondaryFixed
val ColorScheme.onSecondaryFixed: Color get() = OnSecondaryFixed
val ColorScheme.tertiaryFixed: Color get() = TertiaryFixed
val ColorScheme.onTertiaryFixedVariant: Color get() = OnTertiaryFixedVariant
