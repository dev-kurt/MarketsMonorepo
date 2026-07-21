package com.devkurt.markets.watchlist.ui.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.devkurt.markets.coin_detail.ui.api.CoinDetailRoute
import com.devkurt.markets.navigation.api.LocalGraphMain
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.feedback.MkCircularProgressIndicator
import com.devkurt.markets.ui.api.feedback.MkFeedbackPlaceholder
import com.devkurt.markets.ui.api.feedback.MkFeedbackType
import com.devkurt.markets.ui.api.testing.mkTestTag
import com.devkurt.markets.ui.api.theme.MkTheme
import com.devkurt.markets.watchlist.ui.impl.section.WatchlistCoinRow

@Composable
fun WatchlistScreen(
    state: WatchlistState,
    onEvent: (WatchlistEvent) -> Unit,
) {
    val mainGraph = LocalGraphMain.currentOrNull

    when {
        state.error != null && state.coins.isEmpty() -> {
            MkFeedbackPlaceholder(
                message = state.error,
                type = MkFeedbackType.Error,
                onRetry = { onEvent(WatchlistEvent.Retry) },
                modifier = Modifier.padding(MkTheme.padding.lg),
            )
        }

        state.isLoading && state.coins.isEmpty() -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MkTheme.padding.lg),
                contentAlignment = Alignment.Center,
            ) {
                MkCircularProgressIndicator()
            }
        }

        state.coins.isEmpty() -> {
            MkFeedbackPlaceholder(
                message = stringResource(R.string.watchlist_empty),
                type = MkFeedbackType.Info,
                modifier = Modifier.padding(MkTheme.padding.lg),
            )
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .mkTestTag("watchlist_sheet"),
                contentPadding = PaddingValues(MkTheme.padding.md),
                verticalArrangement = Arrangement.spacedBy(MkTheme.padding.sm),
            ) {
                item {
                    MkText(
                        text = stringResource(R.string.watchlist_title),
                        style = MkTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = MkTheme.padding.sm),
                    )
                }
                items(state.coins, key = { it.id }) { coin ->
                    WatchlistCoinRow(
                        coin = coin,
                        onClick = { mainGraph?.add(CoinDetailRoute(coin.id)) },
                        onRemove = { onEvent(WatchlistEvent.RemoveClicked(coin.id)) },
                    )
                }
            }
        }
    }
}
