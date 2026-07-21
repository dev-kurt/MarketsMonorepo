package com.devkurt.markets.network.di

import com.devkurt.markets.network.api.NetworkConfig
import com.devkurt.markets.network.impl.NetworkClientFactory
import com.devkurt.markets.serialization.api.MkJson
import io.ktor.client.HttpClient
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
class NetworkModule {
    @Single
    fun httpClient(config: NetworkConfig): HttpClient =
        NetworkClientFactory.create(json = MkJson.instance, config = config)
}
