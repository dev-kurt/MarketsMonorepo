package com.devkurt.markets.watchlist.data.local

import kotlinx.serialization.Serializable

@Serializable
data class WatchlistData(
    val coinIds: Set<String> = emptySet(),
)
