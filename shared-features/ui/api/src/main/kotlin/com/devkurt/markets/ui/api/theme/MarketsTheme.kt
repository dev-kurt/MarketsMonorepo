package com.devkurt.markets.ui.api.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun MarketsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) MkColorScheme.dark() else MkColorScheme.light()

    CompositionLocalProvider(
        LocalMkColorScheme provides colorScheme,
        LocalMkTypography provides MkTypography(),
        LocalMkShapes provides MkShapes(),
        LocalMkPadding provides MkPadding(),
    ) {
        Surface(
            color = colorScheme.background,
            contentColor = colorScheme.onBackground,
            content = content,
        )
    }
}
