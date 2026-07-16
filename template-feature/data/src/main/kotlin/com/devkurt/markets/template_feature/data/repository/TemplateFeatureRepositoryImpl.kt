package com.devkurt.markets.template_feature.data.repository

import com.devkurt.markets.network.api.toResult
import com.devkurt.markets.template_feature.data.mapper.toTemplateFeature
import com.devkurt.markets.template_feature.data.remote.api.TemplateFeatureRemoteApi
import com.devkurt.markets.template_feature.domain.api.model.TemplateFeature
import com.devkurt.markets.template_feature.domain.api.repository.TemplateFeatureRepository

class TemplateFeatureRepositoryImpl(
    private val templateFeatureRemoteApi: TemplateFeatureRemoteApi,
) : TemplateFeatureRepository {

    override suspend fun getTemplateFeature(): Result<TemplateFeature> {
        return templateFeatureRemoteApi.getTemplateFeature().toResult()
            .map { templateFeatureResponse ->
                templateFeatureResponse.toTemplateFeature()
            }
    }
}
