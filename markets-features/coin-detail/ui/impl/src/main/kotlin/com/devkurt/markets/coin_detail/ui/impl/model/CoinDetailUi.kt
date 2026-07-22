package com.devkurt.markets.coin_detail.ui.impl.model

import androidx.compose.runtime.Immutable

@Immutable
data class CoinDetailUi(
    val id: String,
    val symbol: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val marketCapRank: Int,
    val formattedPrice: String,
    val formattedChange: String,
    val isPriceUp: Boolean,
    val formattedMarketCap: String,
    val formattedTotalVolume: String,
    val formattedHigh24h: String,
    val formattedLow24h: String,
    val formattedAllTimeHigh: String,
    val formattedAllTimeLow: String,
    val formattedCirculatingSupply: String,
    val formattedMaxSupply: String?,
)
