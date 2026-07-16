package com.devkurt.markets.template_feature.ui.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun TemplateFeatureWrapper(
    viewModel: TemplateFeatureViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    TemplateFeatureScreen(
        state = state,
        onEvent = onEvent,
    )
}
