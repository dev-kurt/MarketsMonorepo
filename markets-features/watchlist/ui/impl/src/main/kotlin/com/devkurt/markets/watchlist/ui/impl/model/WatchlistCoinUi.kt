package com.devkurt.markets.watchlist.ui.impl.model

import androidx.compose.runtime.Immutable

@Immutable
data class WatchlistCoinUi(
    val id: String,
    val symbol: String,
    val name: String,
    val imageUrl: String,
    val formattedPrice: String,
    val formattedChange: String,
    val isPriceUp: Boolean,
)
