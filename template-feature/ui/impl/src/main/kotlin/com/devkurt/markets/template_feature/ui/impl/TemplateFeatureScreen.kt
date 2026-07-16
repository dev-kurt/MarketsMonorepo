package com.devkurt.markets.template_feature.ui.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devkurt.markets.template_feature.ui.impl.section.TemplateFeatureTopBar
import com.devkurt.markets.ui.api.buttons.MkTextButton
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.feedback.MkError
import com.devkurt.markets.ui.api.frame.MkScreenScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateFeatureScreen(
    state: TemplateFeatureState,
    onEvent: (TemplateFeatureEvent) -> Unit,
) {
    MkScreenScaffold(
        topBar = {
            TemplateFeatureTopBar(state, onEvent)
        },
        isLoading = state.isLoading,
    ) { paddingValues ->
        state.error?.let { error ->
            MkError(
                message = error,
                action = {
                    MkTextButton(onClick = { onEvent(TemplateFeatureEvent.Refresh) }) {
                        MkText("Tekrar dene")
                    }
                },
            )
            return@MkScreenScaffold
        }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            MkText(text = state.name)
        }
    }
}
