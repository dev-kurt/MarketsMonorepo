package com.devkurt.markets.dashboard.ui.impl

import androidx.compose.runtime.Immutable
import com.devkurt.markets.watchlist.domain.api.model.WatchlistCoin

@Immutable
data class DashboardState(
    val isLoading: Boolean = false,
    val watchlistCoins: List<WatchlistCoin> = emptyList(),
    val error: String? = null,
)
