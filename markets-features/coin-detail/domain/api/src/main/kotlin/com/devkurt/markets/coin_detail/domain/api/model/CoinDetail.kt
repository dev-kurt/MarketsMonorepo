package com.devkurt.markets.coin_detail.domain.api.model

data class CoinDetail(
    val id: String,
    val symbol: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val marketCapRank: Int,
    val price: Double,
    val changePercent24h: Double,
    val isPriceUp: Boolean,
    val marketCap: Double,
    val totalVolume: Double,
    val high24h: Double,
    val low24h: Double,
    val allTimeHigh: Double,
    val allTimeLow: Double,
    val circulatingSupply: Double,
    val maxSupply: Double?,
)
