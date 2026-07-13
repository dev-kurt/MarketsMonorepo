package com.devkurt.markets.ui.api.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/** Tipografi ölçeği — Material'dan bağımsız (referans projedeki BMTypography deseni). */
@Immutable
data class MkTypography(
    val titleLarge: TextStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp),
    val titleMedium: TextStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp),
    val body: TextStyle = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp),
    val bodyStrong: TextStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp),
    val label: TextStyle = TextStyle(fontWeight = FontWeight.Medium, fontSize = 12.sp),
)

val LocalMkTypography = staticCompositionLocalOf { MkTypography() }
