package com.devkurt.markets.ui.api.feedback

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.devkurt.markets.ui.api.theme.MkTheme

@Composable
fun MkLinearProgressIndicator(
    progress: () -> Float,
    modifier: Modifier = Modifier,
    color: Color = MkTheme.colorScheme.primary,
    trackColor: Color = Color.Transparent,
) {
    LinearProgressIndicator(
        progress = progress,
        modifier = modifier,
        color = color,
        trackColor = trackColor,
    )
}

@Composable
fun MkLinearProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MkTheme.colorScheme.primary,
    trackColor: Color = Color.Transparent,
) {
    LinearProgressIndicator(
        modifier = modifier,
        color = color,
        trackColor = trackColor,
    )
}
