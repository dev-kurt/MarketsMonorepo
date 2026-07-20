package com.devkurt.markets.watchlist.data.mapper

import com.devkurt.markets.watchlist.data.remote.dto.WatchlistCoinDto
import com.devkurt.markets.watchlist.domain.api.model.WatchlistCoin

fun WatchlistCoinDto.toWatchlistCoin(): WatchlistCoin = WatchlistCoin(
    id = id,
    symbol = symbol.uppercase(),
    name = name,
    imageUrl = image,
    price = currentPrice,
    changePercent24h = priceChangePercentage24h ?: 0.0,
)
