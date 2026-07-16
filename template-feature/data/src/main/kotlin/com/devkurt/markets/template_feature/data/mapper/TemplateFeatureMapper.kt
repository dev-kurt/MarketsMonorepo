package com.devkurt.markets.template_feature.data.mapper

import com.devkurt.markets.template_feature.data.remote.dto.TemplateFeatureResponse
import com.devkurt.markets.template_feature.domain.api.model.TemplateFeature

fun TemplateFeatureResponse.toTemplateFeature(): TemplateFeature {
    return TemplateFeature(
        name = data,
    )
}
