package com.devkurt.markets.coins_list.data.mapper

import com.devkurt.markets.coins_list.data.remote.dto.CoinMarketDto
import com.devkurt.markets.coins_list.domain.api.model.Coin

fun CoinMarketDto.toCoin(): Coin = Coin(
    id = id,
    symbol = symbol.uppercase(),
    name = name,
    imageUrl = image,
    price = currentPrice ?: 0.0,
    changePercent24h = priceChangePercentage24h ?: 0.0,
    marketCapRank = marketCapRank ?: 0,
)
