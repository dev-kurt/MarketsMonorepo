package com.devkurt.markets.ui.api.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devkurt.markets.ui.api.theme.MkTheme

@Composable
private fun mkCardColors(): CardColors = CardDefaults.cardColors(
    containerColor = MkTheme.colorScheme.surface,
    contentColor = MkTheme.colorScheme.onSurface,
    disabledContainerColor = MkTheme.colorScheme.surfaceVariant,
    disabledContentColor = MkTheme.colorScheme.onSurfaceVariant,
)

@Composable
fun MkCard(
    modifier: Modifier = Modifier,
    shape: Shape = MkTheme.shapes.medium,
    colors: CardColors = mkCardColors(),
    border: BorderStroke? = BorderStroke(Dp.Hairline, MkTheme.colorScheme.outline),
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier,
        shape = shape,
        colors = colors,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = border,
        content = content,
    )
}

@Composable
fun MkCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = MkTheme.shapes.medium,
    colors: CardColors = mkCardColors(),
    border: BorderStroke? = BorderStroke(Dp.Hairline, MkTheme.colorScheme.outline),
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = border,
        content = content,
    )
}
