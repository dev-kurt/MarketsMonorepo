package com.devkurt.markets.network.api

data class NetworkConfig(
    val baseUrl: String,
    val defaultHeaders: Map<String, String> = emptyMap(),
)
