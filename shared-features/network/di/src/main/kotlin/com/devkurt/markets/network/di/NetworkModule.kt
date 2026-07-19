package com.devkurt.markets.network.di

import com.devkurt.markets.network.api.NetworkConfig
import com.devkurt.markets.network.impl.NetworkClientFactory
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
class NetworkModule {
    @Single
    fun json(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Single
    fun httpClient(json: Json, config: NetworkConfig): HttpClient =
        NetworkClientFactory.create(json = json, config = config)
}
