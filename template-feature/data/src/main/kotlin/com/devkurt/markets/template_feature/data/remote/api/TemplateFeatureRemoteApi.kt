package com.devkurt.markets.template_feature.data.remote.api

import com.devkurt.markets.network.api.NetworkResult
import com.devkurt.markets.network.api.safeApiCall
import com.devkurt.markets.template_feature.data.remote.dto.TemplateFeatureResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get

private const val ENDPOINT = "xx"

class TemplateFeatureRemoteApi(
    private val httpClient: HttpClient,
) {

    suspend fun getTemplateFeature(): NetworkResult<TemplateFeatureResponse> {
        return safeApiCall {
            httpClient.get(ENDPOINT)
        }
    }
}
