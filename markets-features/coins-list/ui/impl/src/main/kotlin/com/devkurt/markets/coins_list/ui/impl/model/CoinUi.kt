package com.devkurt.markets.coins_list.ui.impl.model

import androidx.compose.runtime.Immutable

@Immutable
data class CoinUi(
    val id: String,
    val symbol: String,
    val name: String,
    val imageUrl: String,
    val marketCapRank: String,
    val formattedPrice: String,
    val formattedChange: String,
    val isPriceUp: Boolean,
)
