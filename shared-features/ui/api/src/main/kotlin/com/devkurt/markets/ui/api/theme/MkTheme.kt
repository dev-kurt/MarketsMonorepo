package com.devkurt.markets.ui.api.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

/**
 * MaterialTheme'i tamamlayan ekstra token erişimi (referans projedeki BMTheme deseni).
 * Standart renk/tipografi MaterialTheme'den gelir; domaine özel token'lar buradan:
 *   MkTheme.colors.priceUp, MkTheme.padding.md
 */
object MkTheme {
    val colors: MkColors
        @Composable @ReadOnlyComposable get() = LocalMkColors.current

    val padding: MkPadding
        @Composable @ReadOnlyComposable get() = LocalMkPadding.current
}
