package com.devkurt.markets.coins_list.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinsListResponse(
    @SerialName("data")
    val data: String,
)
