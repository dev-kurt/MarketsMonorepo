package com.devkurt.markets.coins_list.domain.api.model

import androidx.compose.runtime.Immutable

@Immutable
data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val changePercent24h: Double,
    val marketCapRank: Int,
) {
    val isPriceUp: Boolean get() = changePercent24h >= 0
}
