package com.devkurt.markets.coins_list.data.mapper

import com.devkurt.markets.coins_list.data.remote.dto.CoinMarketDto
import com.devkurt.markets.coins_list.domain.api.model.Coin

fun CoinMarketDto.toCoin(): Coin = Coin(
    id = id,
    symbol = symbol.uppercase(),
    name = name,
    imageUrl = image,
    price = currentPrice,
    changePercent24h = priceChangePercentage24h,
    marketCapRank = marketCapRank,
)
