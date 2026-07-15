package com.devkurt.markets.ui.api.layout

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.devkurt.markets.ui.api.theme.MkTheme

@Composable
fun MkHorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = Dp.Hairline,
    color: Color = MkTheme.colorScheme.outline,
) {
    HorizontalDivider(modifier = modifier, thickness = thickness, color = color)
}

@Composable
fun MkVerticalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = Dp.Hairline,
    color: Color = MkTheme.colorScheme.outline,
) {
    VerticalDivider(modifier = modifier, thickness = thickness, color = color)
}
