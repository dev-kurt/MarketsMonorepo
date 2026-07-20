package com.devkurt.markets.dev_tools.ui.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devkurt.markets.dev_tools.ui.impl.section.DevToolsActionRow
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.theme.MkTheme

@Composable
fun DevToolsScreen(
    state: DevToolsState,
    onEvent: (DevToolsEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MkTheme.padding.lg),
        verticalArrangement = Arrangement.spacedBy(MkTheme.padding.sm),
    ) {
        MkText(text = "Developer Tools", style = MkTheme.typography.titleMedium)
        MkText(
            text = "Debug builds only",
            color = MkTheme.colorScheme.onSurfaceVariant,
            style = MkTheme.typography.label,
        )

        state.actionTitles.forEachIndexed { index, title ->
            DevToolsActionRow(
                title = title,
                enabled = !state.isLoading,
                onClick = { onEvent(DevToolsEvent.ExecuteAction(index)) },
            )
        }
    }
}
