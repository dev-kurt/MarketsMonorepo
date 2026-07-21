package com.devkurt.markets.coin_detail.data.mapper

import com.devkurt.markets.coin_detail.data.remote.dto.CoinDetailDto
import com.devkurt.markets.coin_detail.domain.api.model.CoinDetail

private const val VS_CURRENCY = "usd"

fun CoinDetailDto.toCoinDetail(): CoinDetail = CoinDetail(
    id = id,
    symbol = symbol.uppercase(),
    name = name,
    description = description.en,
    imageUrl = image.large,
    marketCapRank = marketCapRank,
    price = marketData.currentPrice.inVsCurrency(),
    changePercent24h = marketData.priceChangePercentage24h,
    marketCap = marketData.marketCap.inVsCurrency(),
    totalVolume = marketData.totalVolume.inVsCurrency(),
    high24h = marketData.high24h.inVsCurrency(),
    low24h = marketData.low24h.inVsCurrency(),
    allTimeHigh = marketData.ath.inVsCurrency(),
    allTimeLow = marketData.atl.inVsCurrency(),
    circulatingSupply = marketData.circulatingSupply,
    maxSupply = marketData.maxSupply,
)

private fun Map<String, Double>.inVsCurrency(): Double = this[VS_CURRENCY] ?: 0.0
