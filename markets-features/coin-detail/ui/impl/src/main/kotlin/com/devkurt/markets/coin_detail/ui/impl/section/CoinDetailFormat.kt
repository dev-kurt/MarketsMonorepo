package com.devkurt.markets.coin_detail.ui.impl.section

import java.util.Locale

fun Double.asPrice(): String {
    val pattern = if (this >= 1.0) "$%,.2f" else "$%,.6f"
    return String.format(Locale.US, pattern, this)
}

fun Double.asChangePercent(): String = String.format(Locale.US, "%+.2f%%", this)

fun Double.asCompactAmount(): String = when {
    this >= 1_000_000_000 -> String.format(Locale.US, "$%,.2fB", this / 1_000_000_000)
    this >= 1_000_000 -> String.format(Locale.US, "$%,.2fM", this / 1_000_000)
    else -> String.format(Locale.US, "$%,.0f", this)
}

fun Double.asSupply(): String = String.format(Locale.US, "%,.0f", this)
