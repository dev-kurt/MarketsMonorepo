package com.devkurt.markets

import android.app.Application
import com.devkurt.markets.graph_main.di.StartKoin
import com.devkurt.markets.network.api.NetworkConfig

private const val COIN_GECKO_BASE_URL = "https://api.coingecko.com/api/v3/"
private const val COIN_GECKO_API_KEY_HEADER = "x-cg-demo-api-key"

class MarketsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        StartKoin.doInitKoin(
            context = this,
            networkConfig = NetworkConfig(
                baseUrl = COIN_GECKO_BASE_URL,
                defaultHeaders = BuildConfig.CG_DEMO_API_KEY
                    .takeIf { it.isNotBlank() }
                    ?.let { mapOf(COIN_GECKO_API_KEY_HEADER to it) }
                    .orEmpty(),
            ),
        )
    }
}
