package com.devkurt.markets.search.domain.api.model

import androidx.compose.runtime.Immutable

@Immutable
data class SearchCoin(
    val id: String,
    val name: String,
    val symbol: String,
    val marketCapRank: Int?,
    val imageUrl: String,
)
