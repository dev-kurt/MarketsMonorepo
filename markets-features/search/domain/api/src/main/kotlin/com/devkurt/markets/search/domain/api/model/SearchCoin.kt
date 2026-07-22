package com.devkurt.markets.search.domain.api.model

data class SearchCoin(
    val id: String,
    val name: String,
    val symbol: String,
    val marketCapRank: Int?,
    val imageUrl: String,
)
