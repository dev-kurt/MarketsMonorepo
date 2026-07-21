package com.devkurt.markets.coin_detail.ui.impl.section

import java.util.Locale

private const val ONE_BILLION = 1_000_000_000.0
private const val ONE_MILLION = 1_000_000.0

fun Double.asPrice(): String {
    val pattern = if (this >= 1.0) "$%,.2f" else "$%,.6f"
    return String.format(Locale.US, pattern, this)
}

fun Double.asChangePercent(): String = String.format(Locale.US, "%+.2f%%", this)

fun Double.asCompactAmount(): String = when {
    this >= ONE_BILLION -> String.format(Locale.US, "$%,.2fB", this / ONE_BILLION)
    this >= ONE_MILLION -> String.format(Locale.US, "$%,.2fM", this / ONE_MILLION)
    else -> String.format(Locale.US, "$%,.0f", this)
}

fun Double.asSupply(): String = String.format(Locale.US, "%,.0f", this)
