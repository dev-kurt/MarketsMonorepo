package com.devkurt.markets.ui.api.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/** Material'da olmayan, domaine özel ekstra renkler (fiyat yönü). */
@Immutable
data class MkColors(
    val priceUp: Color,
    val priceDown: Color,
)

val LocalMkColors = staticCompositionLocalOf<MkColors> {
    error("MkColors sağlanmadı — MarketsTheme içinde kullanın")
}

internal val LightMkColors = MkColors(
    priceUp = Color(0xFF17B26A),
    priceDown = Color(0xFFE5484D),
)

internal val DarkMkColors = MkColors(
    priceUp = Color(0xFF2DD4A0),
    priceDown = Color(0xFFFF6B6B),
)
