package com.devkurt.markets.ui.api.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object MkTheme {
    val colorScheme: MkColorScheme
        @Composable @ReadOnlyComposable get() = LocalMkColorScheme.current

    val typography: MkTypography
        @Composable @ReadOnlyComposable get() = LocalMkTypography.current

    val shapes: MkShapes
        @Composable @ReadOnlyComposable get() = LocalMkShapes.current

    val padding: MkPadding
        @Composable @ReadOnlyComposable get() = LocalMkPadding.current
}
