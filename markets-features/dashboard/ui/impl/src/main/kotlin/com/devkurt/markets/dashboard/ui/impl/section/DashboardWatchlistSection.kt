package com.devkurt.markets.dashboard.ui.impl.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.devkurt.markets.dashboard.ui.impl.R
import com.devkurt.markets.ui.api.buttons.MkTextButton
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.theme.MkTheme
import com.devkurt.markets.watchlist.domain.api.model.WatchlistCoin
import com.devkurt.markets.watchlist.ui.impl.section.WatchlistCoinRow

@Composable
fun DashboardWatchlistSection(
    coins: List<WatchlistCoin>,
    onCoinClick: (String) -> Unit,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MkTheme.padding.sm),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MkText(
                text = stringResource(R.string.dashboard_watchlist_title),
                style = MkTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
            )
            MkTextButton(onClick = onSeeAllClick) {
                MkText(stringResource(R.string.dashboard_watchlist_see_all))
            }
        }

        if (coins.isEmpty()) {
            MkText(
                text = stringResource(R.string.dashboard_watchlist_empty),
                color = MkTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = MkTheme.padding.md),
            )
        } else {
            coins.forEach { coin ->
                WatchlistCoinRow(
                    coin = coin,
                    onClick = { onCoinClick(coin.id) },
                )
            }
        }
    }
}
