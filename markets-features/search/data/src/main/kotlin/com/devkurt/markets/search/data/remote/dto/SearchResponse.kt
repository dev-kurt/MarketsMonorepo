package com.devkurt.markets.search.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val coins: List<SearchCoinDto> = emptyList(),
)

@Serializable
data class SearchCoinDto(
    val id: String,
    val name: String,
    val symbol: String,
    @SerialName("market_cap_rank")
    val marketCapRank: Int? = null,
    val large: String,
)
