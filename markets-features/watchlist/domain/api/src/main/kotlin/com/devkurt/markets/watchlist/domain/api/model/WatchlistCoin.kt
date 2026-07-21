package com.devkurt.markets.watchlist.domain.api.model

import androidx.compose.runtime.Immutable

@Immutable
data class WatchlistCoin(
    val id: String,
    val symbol: String,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val changePercent24h: Double,
) {
    val isPriceUp: Boolean get() = changePercent24h >= 0
}
