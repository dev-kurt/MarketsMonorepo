package com.devkurt.markets.watchlist.ui.impl.mapper

import com.devkurt.markets.watchlist.domain.api.model.WatchlistCoin
import com.devkurt.markets.watchlist.ui.impl.model.WatchlistCoinUi
import java.util.Locale

fun WatchlistCoin.toUi(): WatchlistCoinUi = WatchlistCoinUi(
    id = id,
    symbol = symbol,
    name = name,
    imageUrl = imageUrl,
    formattedPrice = price.asPrice(),
    formattedChange = changePercent24h.asChangePercent(),
    isPriceUp = isPriceUp,
)

private fun Double.asPrice(): String {
    val pattern = if (this >= 1.0) "$%,.2f" else "$%,.6f"
    return String.format(Locale.US, pattern, this)
}

private fun Double.asChangePercent(): String = String.format(Locale.US, "%+.2f%%", this)
