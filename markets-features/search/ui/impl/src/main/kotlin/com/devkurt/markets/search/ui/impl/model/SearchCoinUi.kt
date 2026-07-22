package com.devkurt.markets.search.ui.impl.model

import androidx.compose.runtime.Immutable

@Immutable
data class SearchCoinUi(
    val id: String,
    val name: String,
    val symbol: String,
    val marketCapRank: Int?,
    val imageUrl: String,
)
