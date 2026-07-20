package com.devkurt.markets.coins_list.ui.impl.section

import com.devkurt.markets.coins_list.domain.api.model.Coin
import java.util.Locale

fun Coin.formattedPrice(): String {
    val pattern = if (price >= 1.0) "$%,.2f" else "$%,.6f"
    return String.format(Locale.US, pattern, price)
}

fun Coin.formattedChange(): String =
    String.format(Locale.US, "%+.2f%%", changePercent24h)
