package com.devkurt.markets.template_feature.ui.impl

import androidx.compose.runtime.Immutable

@Immutable
data class TemplateFeatureState(
    val isLoading: Boolean = false,
    val name: String = "",
    val error: String? = null,
)
