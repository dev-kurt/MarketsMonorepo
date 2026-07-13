package com.devkurt.markets.ui.api.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

/**
 * Uygulama teması. Altta markalı MaterialTheme (standart component'ler stilli çalışır),
 * üstte MkTheme ekstra token'ları (priceUp/priceDown, padding).
 */
@Composable
fun MarketsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalMkColors provides if (darkTheme) DarkMkColors else LightMkColors,
        LocalMkPadding provides MkPadding(),
    ) {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkColors else LightColors,
            typography = MarketsTypography,
            content = content,
        )
    }
}
