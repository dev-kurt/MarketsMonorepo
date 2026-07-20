package com.devkurt.markets.watchlist.ui.impl

import androidx.compose.runtime.Immutable
import com.devkurt.markets.watchlist.domain.api.model.WatchlistCoin

@Immutable
data class WatchlistState(
    val isLoading: Boolean = false,
    val coins: List<WatchlistCoin> = emptyList(),
    val error: String? = null,
)
