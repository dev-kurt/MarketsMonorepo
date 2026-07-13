package com.devkurt.markets.ui.api.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// ─── Marka renkleri ───
private val Emerald = Color(0xFF00B386)
private val EmeraldLight = Color(0xFF33C9A3)
private val Ink = Color(0xFF0E1116)
private val Cloud = Color(0xFFF6F8FA)
private val InkSurface = Color(0xFF161A20)

internal val LightColors = lightColorScheme(
    primary = Emerald,
    onPrimary = Color.White,
    secondary = Color(0xFF12324B),
    onSecondary = Color.White,
    background = Cloud,
    onBackground = Ink,
    surface = Color.White,
    onSurface = Ink,
    surfaceVariant = Color(0xFFEDF1F5),
    onSurfaceVariant = Color(0xFF5B6470),
    outline = Color(0xFFCBD3DB),
    error = Color(0xFFE5484D),
    onError = Color.White,
)

internal val DarkColors = darkColorScheme(
    primary = EmeraldLight,
    onPrimary = Color(0xFF00281E),
    secondary = Color(0xFF9DB8CC),
    onSecondary = Ink,
    background = Ink,
    onBackground = Color(0xFFECEFF3),
    surface = InkSurface,
    onSurface = Color(0xFFECEFF3),
    surfaceVariant = Color(0xFF1F242B),
    onSurfaceVariant = Color(0xFF9AA4AF),
    outline = Color(0xFF39424C),
    error = Color(0xFFFF6B6B),
    onError = Color(0xFF3A0A0A),
)
