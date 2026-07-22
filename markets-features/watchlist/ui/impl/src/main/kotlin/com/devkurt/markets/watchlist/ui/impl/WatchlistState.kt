package com.devkurt.markets.watchlist.ui.impl

import androidx.compose.runtime.Immutable
import com.devkurt.markets.watchlist.ui.impl.model.WatchlistCoinUi

@Immutable
data class WatchlistState(
    val isLoading: Boolean = false,
    val coins: List<WatchlistCoinUi> = emptyList(),
    val error: String? = null,
)
