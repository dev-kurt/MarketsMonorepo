package com.devkurt.markets.ui.api.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.devkurt.markets.ui.api.theme.MkTheme

@Composable
private fun mkButtonColors(): ButtonColors = ButtonDefaults.buttonColors(
    containerColor = MkTheme.colorScheme.primary,
    contentColor = MkTheme.colorScheme.onPrimary,
    disabledContainerColor = MkTheme.colorScheme.onSurface.copy(alpha = 0.12f),
    disabledContentColor = MkTheme.colorScheme.onSurface.copy(alpha = 0.38f),
)

@Composable
fun MkButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = MkTheme.shapes.large,
    colors: ButtonColors = mkButtonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun MkTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = MkTheme.shapes.medium,
    content: @Composable RowScope.() -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.textButtonColors(contentColor = MkTheme.colorScheme.primary),
        content = content,
    )
}
