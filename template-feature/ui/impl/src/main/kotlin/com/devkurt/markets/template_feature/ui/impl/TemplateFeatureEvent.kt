package com.devkurt.markets.template_feature.ui.impl

sealed interface TemplateFeatureEvent {
    data class UpdateState(
        val update: (TemplateFeatureState) -> TemplateFeatureState,
    ) : TemplateFeatureEvent

    data object Refresh : TemplateFeatureEvent
}
