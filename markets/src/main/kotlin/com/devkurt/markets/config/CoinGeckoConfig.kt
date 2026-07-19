package com.devkurt.markets.config

import com.devkurt.markets.BuildConfig
import com.devkurt.markets.network.api.NetworkConfig

object CoinGeckoConfig {
    private const val BASE_URL = "https://api.coingecko.com/api/v3/"
    private const val API_KEY_HEADER = "x-cg-demo-api-key"

    fun networkConfig(): NetworkConfig = NetworkConfig(
        baseUrl = BASE_URL,
        defaultHeaders = BuildConfig.CG_DEMO_API_KEY
            .takeIf { it.isNotBlank() }
            ?.let { apiKey -> mapOf(API_KEY_HEADER to apiKey) }
            .orEmpty(),
    )
}
