package com.devkurt.markets.dashboard.ui.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devkurt.markets.coin_detail.ui.api.CoinDetailRoute
import com.devkurt.markets.dashboard.ui.impl.section.DashboardTopBar
import com.devkurt.markets.dashboard.ui.impl.section.DashboardWatchlistSection
import com.devkurt.markets.navigation.api.LocalGraphMain
import com.devkurt.markets.ui.api.feedback.MkFeedbackPlaceholder
import com.devkurt.markets.ui.api.feedback.MkFeedbackType
import com.devkurt.markets.ui.api.frame.MkScreenScaffold
import com.devkurt.markets.ui.api.theme.MkTheme
import com.devkurt.markets.watchlist.ui.api.WatchlistRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    state: DashboardState,
    onEvent: (DashboardEvent) -> Unit,
) {
    val mainGraph = LocalGraphMain.currentOrNull

    MkScreenScaffold(
        topBar = { DashboardTopBar() },
        isLoading = state.isLoading,
    ) { paddingValues ->
        if (state.error != null && state.watchlistCoins.isEmpty()) {
            MkFeedbackPlaceholder(
                message = state.error,
                type = MkFeedbackType.Error,
                onRetry = { onEvent(DashboardEvent.Retry) },
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(MkTheme.padding.md),
            )
            return@MkScreenScaffold
        }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(MkTheme.padding.md),
        ) {
            DashboardWatchlistSection(
                coins = state.watchlistCoins,
                onCoinClick = { coinId -> mainGraph?.add(CoinDetailRoute(coinId)) },
                onSeeAllClick = { mainGraph?.add(WatchlistRoute) },
            )
        }
    }
}
