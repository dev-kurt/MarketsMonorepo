package com.devkurt.markets.coins_list.domain.api.model

data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val changePercent24h: Double,
    val isPriceUp: Boolean,
    val marketCapRank: Int,
)
