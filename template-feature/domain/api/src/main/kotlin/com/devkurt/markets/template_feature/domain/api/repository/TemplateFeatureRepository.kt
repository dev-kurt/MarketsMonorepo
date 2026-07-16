package com.devkurt.markets.template_feature.domain.api.repository

import com.devkurt.markets.template_feature.domain.api.model.TemplateFeature

interface TemplateFeatureRepository {

    suspend fun getTemplateFeature(): Result<TemplateFeature>
}
