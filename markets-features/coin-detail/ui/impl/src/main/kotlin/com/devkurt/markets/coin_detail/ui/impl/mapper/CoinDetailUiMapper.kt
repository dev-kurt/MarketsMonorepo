package com.devkurt.markets.coin_detail.ui.impl.mapper

import com.devkurt.markets.coin_detail.domain.api.model.CoinDetail
import com.devkurt.markets.coin_detail.ui.impl.model.CoinDetailUi
import java.util.Locale

private const val ONE_BILLION = 1_000_000_000.0
private const val ONE_MILLION = 1_000_000.0

fun CoinDetail.toUi(): CoinDetailUi = CoinDetailUi(
    id = id,
    symbol = symbol,
    name = name,
    description = description,
    imageUrl = imageUrl,
    marketCapRank = marketCapRank,
    formattedPrice = price.asPrice(),
    formattedChange = changePercent24h.asChangePercent(),
    isPriceUp = isPriceUp,
    formattedMarketCap = marketCap.asCompactAmount(),
    formattedTotalVolume = totalVolume.asCompactAmount(),
    formattedHigh24h = high24h.asPrice(),
    formattedLow24h = low24h.asPrice(),
    formattedAllTimeHigh = allTimeHigh.asPrice(),
    formattedAllTimeLow = allTimeLow.asPrice(),
    formattedCirculatingSupply = circulatingSupply.asSupply(),
    formattedMaxSupply = maxSupply?.asSupply(),
)

private fun Double.asPrice(): String {
    val pattern = if (this >= 1.0) "$%,.2f" else "$%,.6f"
    return String.format(Locale.US, pattern, this)
}

private fun Double.asChangePercent(): String = String.format(Locale.US, "%+.2f%%", this)

private fun Double.asCompactAmount(): String = when {
    this >= ONE_BILLION -> String.format(Locale.US, "$%,.2fB", this / ONE_BILLION)
    this >= ONE_MILLION -> String.format(Locale.US, "$%,.2fM", this / ONE_MILLION)
    else -> String.format(Locale.US, "$%,.0f", this)
}

private fun Double.asSupply(): String = String.format(Locale.US, "%,.0f", this)
