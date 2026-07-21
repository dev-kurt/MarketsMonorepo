package com.devkurt.markets.coin_detail.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinDetailDto(
    val id: String,
    val symbol: String,
    val name: String,
    val description: DescriptionDto = DescriptionDto(),
    val image: ImageDto = ImageDto(),
    @SerialName("market_cap_rank")
    val marketCapRank: Int = 0,
    @SerialName("market_data")
    val marketData: MarketDataDto = MarketDataDto(),
)

@Serializable
data class DescriptionDto(
    val en: String = "",
)

@Serializable
data class ImageDto(
    val large: String = "",
)

@Serializable
data class MarketDataDto(
    @SerialName("current_price")
    val currentPrice: Map<String, Double> = emptyMap(),
    @SerialName("market_cap")
    val marketCap: Map<String, Double> = emptyMap(),
    @SerialName("total_volume")
    val totalVolume: Map<String, Double> = emptyMap(),
    @SerialName("high_24h")
    val high24h: Map<String, Double> = emptyMap(),
    @SerialName("low_24h")
    val low24h: Map<String, Double> = emptyMap(),
    val ath: Map<String, Double> = emptyMap(),
    val atl: Map<String, Double> = emptyMap(),
    @SerialName("price_change_percentage_24h")
    val priceChangePercentage24h: Double = 0.0,
    @SerialName("circulating_supply")
    val circulatingSupply: Double = 0.0,
    @SerialName("max_supply")
    val maxSupply: Double? = null,
)
