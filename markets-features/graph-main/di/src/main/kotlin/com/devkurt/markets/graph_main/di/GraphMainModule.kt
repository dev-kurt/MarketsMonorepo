package com.devkurt.markets.graph_main.di

import com.devkurt.markets.graph_main.ui.impl.GraphMainViewModel
import com.devkurt.markets.network.api.NetworkConfig
import com.devkurt.markets.navigation.api.GraphMain
import com.devkurt.markets.navigation.api.GraphMainRoutes
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

private const val COIN_GECKO_BASE_URL = "https://api.coingecko.com/api/v3/"
private const val COIN_GECKO_API_KEY_HEADER = "x-cg-demo-api-key"

@Module
@Configuration
class GraphMainModule {
    @Single
    fun networkConfig(): NetworkConfig = NetworkConfig(
        baseUrl = COIN_GECKO_BASE_URL,
        defaultHeaders = BuildConfig.CG_DEMO_API_KEY
            .takeIf { it.isNotBlank() }
            ?.let { apiKey -> mapOf(COIN_GECKO_API_KEY_HEADER to apiKey) }
            .orEmpty(),
    )

    @KoinViewModel
    fun graphMainViewModel(
        entryProviders: List<GraphMainRoutes>,
    ): GraphMainViewModel = GraphMainViewModel(
        entryProviders = entryProviders,
    )
}
