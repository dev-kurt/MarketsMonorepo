package com.devkurt.markets.dashboard.ui.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.devkurt.markets.coin_detail.ui.api.CoinDetailRoute
import com.devkurt.markets.dashboard.ui.impl.section.WatchlistSection
import com.devkurt.markets.navigation.api.LocalGraphMain
import com.devkurt.markets.ui.api.bars.MkCenterAlignedTopAppBar
import com.devkurt.markets.ui.api.buttons.MkTextButton
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.feedback.MkError
import com.devkurt.markets.ui.api.frame.MkScreenScaffold
import com.devkurt.markets.ui.api.theme.MkTheme
import com.devkurt.markets.watchlist.ui.api.WatchlistRoute
import com.devkurt.markets.ui.api.R as UiR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    state: DashboardState,
    onEvent: (DashboardEvent) -> Unit,
) {
    val mainGraph = LocalGraphMain.currentOrNull

    MkScreenScaffold(
        topBar = {
            MkCenterAlignedTopAppBar(
                title = { MkText(stringResource(R.string.dashboard_title)) },
            )
        },
        isLoading = state.isLoading,
    ) { paddingValues ->
        if (state.error != null && state.watchlistCoins.isEmpty()) {
            MkError(
                message = state.error,
                action = {
                    MkTextButton(onClick = { onEvent(DashboardEvent.Retry) }) {
                        MkText(stringResource(UiR.string.mk_retry))
                    }
                },
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
            WatchlistSection(
                coins = state.watchlistCoins,
                onCoinClick = { coinId -> mainGraph?.add(CoinDetailRoute(coinId)) },
                onSeeAllClick = { mainGraph?.add(WatchlistRoute) },
            )
        }
    }
}
