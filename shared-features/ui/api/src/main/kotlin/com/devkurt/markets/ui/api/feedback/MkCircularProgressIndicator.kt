package com.devkurt.markets.ui.api.feedback

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.devkurt.markets.ui.api.theme.MkTheme

@Composable
fun MkCircularProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MkTheme.colorScheme.primary,
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color,
    )
}
