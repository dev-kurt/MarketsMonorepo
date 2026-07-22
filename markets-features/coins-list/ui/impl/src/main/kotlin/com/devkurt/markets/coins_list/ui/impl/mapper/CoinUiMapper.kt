package com.devkurt.markets.coins_list.ui.impl.mapper

import com.devkurt.markets.coins_list.domain.api.model.Coin
import com.devkurt.markets.coins_list.ui.impl.model.CoinUi
import java.util.Locale

fun Coin.toUi(): CoinUi = CoinUi(
    id = id,
    symbol = symbol,
    name = name,
    imageUrl = imageUrl,
    marketCapRank = marketCapRank.toString(),
    formattedPrice = price.asPrice(),
    formattedChange = changePercent24h.asChangePercent(),
    isPriceUp = isPriceUp,
)

private fun Double.asPrice(): String {
    val pattern = if (this >= 1.0) "$%,.2f" else "$%,.6f"
    return String.format(Locale.US, pattern, this)
}

private fun Double.asChangePercent(): String = String.format(Locale.US, "%+.2f%%", this)
