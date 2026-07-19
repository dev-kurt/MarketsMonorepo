package com.devkurt.markets.coins_list.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinMarketDto(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @SerialName("current_price")
    val currentPrice: Double,
    @SerialName("price_change_percentage_24h")
    val priceChangePercentage24h: Double? = null,
    @SerialName("market_cap_rank")
    val marketCapRank: Int? = null,
)
