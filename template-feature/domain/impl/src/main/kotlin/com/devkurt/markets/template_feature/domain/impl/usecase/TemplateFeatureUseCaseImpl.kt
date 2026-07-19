package com.devkurt.markets.template_feature.domain.impl.usecase

import com.devkurt.markets.template_feature.domain.api.model.TemplateFeature
import com.devkurt.markets.template_feature.domain.api.repository.TemplateFeatureRepository
import com.devkurt.markets.template_feature.domain.api.usecase.TemplateFeatureUseCase

class TemplateFeatureUseCaseImpl(
    private val repository: TemplateFeatureRepository,
) : TemplateFeatureUseCase {
    override suspend operator fun invoke(): Result<TemplateFeature> =
        repository.getTemplateFeature()
}
