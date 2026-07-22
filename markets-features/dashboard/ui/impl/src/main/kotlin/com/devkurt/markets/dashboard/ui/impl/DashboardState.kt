package com.devkurt.markets.dashboard.ui.impl

import androidx.compose.runtime.Immutable
import com.devkurt.markets.watchlist.ui.impl.model.WatchlistCoinUi

@Immutable
data class DashboardState(
    val isLoading: Boolean = false,
    val watchlistCoins: List<WatchlistCoinUi> = emptyList(),
    val error: String? = null,
)
