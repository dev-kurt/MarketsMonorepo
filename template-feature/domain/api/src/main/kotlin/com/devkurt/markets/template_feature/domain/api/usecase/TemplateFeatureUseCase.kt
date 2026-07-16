package com.devkurt.markets.template_feature.domain.api.usecase

import com.devkurt.markets.template_feature.domain.api.model.TemplateFeature

interface TemplateFeatureUseCase {
    suspend operator fun invoke(): Result<TemplateFeature>
}
