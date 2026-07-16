package com.devkurt.markets.ui.api.buttons

import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.devkurt.markets.ui.api.theme.MkTheme

@Composable
private fun mkIconButtonColors(): IconButtonColors = IconButtonDefaults.iconButtonColors(
    containerColor = Color.Transparent,
    contentColor = LocalContentColor.current,
    disabledContainerColor = Color.Transparent,
    disabledContentColor = MkTheme.colorScheme.onSurface.copy(alpha = 0.38f),
)

@Composable
fun MkIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = mkIconButtonColors(),
    content: @Composable () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        content = content,
    )
}
