package com.devkurt.markets.coin_detail.ui.impl.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.theme.MkTheme

@Composable
fun CoinDetailStatRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        MkText(
            text = label,
            color = MkTheme.colorScheme.onSurfaceVariant,
            style = MkTheme.typography.label,
        )
        MkText(text = value, maxLines = 1)
    }
}
