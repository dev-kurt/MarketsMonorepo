package com.devkurt.markets.dev_tools.ui.impl.section

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devkurt.markets.ui.api.buttons.MkTextButton
import com.devkurt.markets.ui.api.display.MkText

@Composable
fun DevToolsActionRow(
    title: String,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    MkTextButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
    ) {
        MkText(text = title)
    }
}
