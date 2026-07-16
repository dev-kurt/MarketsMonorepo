package com.devkurt.markets.template_feature.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TemplateFeatureResponse(
    @SerialName("data")
    val data: String,
)
