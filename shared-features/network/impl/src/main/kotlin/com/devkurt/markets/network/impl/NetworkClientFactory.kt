package com.devkurt.markets.network.impl

import com.devkurt.markets.network.api.NetworkConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val REQUEST_TIMEOUT_MS = 30_000L
private const val CONNECT_TIMEOUT_MS = 15_000L

object NetworkClientFactory {
    fun create(json: Json, config: NetworkConfig): HttpClient = HttpClient(OkHttp) {
        expectSuccess = true

        install(DefaultRequest) {
            url(config.baseUrl)
            header(HttpHeaders.Accept, ContentType.Application.Json)
            config.defaultHeaders.forEach { (name, value) ->
                header(name, value)
            }
        }

        install(ContentNegotiation) {
            json(json)
        }

        install(Logging) {
            level = if (BuildConfig.DEBUG) LogLevel.INFO else LogLevel.NONE
        }

        install(HttpTimeout) {
            requestTimeoutMillis = REQUEST_TIMEOUT_MS
            connectTimeoutMillis = CONNECT_TIMEOUT_MS
        }
    }
}
