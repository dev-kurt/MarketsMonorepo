package com.devkurt.markets.template_feature.ui.impl.section

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import com.devkurt.markets.navigation.api.LocalGraphMain
import com.devkurt.markets.navigation.api.safePop
import com.devkurt.markets.template_feature.ui.impl.TemplateFeatureEvent
import com.devkurt.markets.template_feature.ui.impl.TemplateFeatureState
import com.devkurt.markets.ui.api.bars.MkCenterAlignedTopAppBar
import com.devkurt.markets.ui.api.buttons.MkIconButton
import com.devkurt.markets.ui.api.display.MkIcon
import com.devkurt.markets.ui.api.display.MkText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateFeatureTopBar(
    state: TemplateFeatureState,
    onEvent: (TemplateFeatureEvent) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    val mainGraph = LocalGraphMain.currentOrNull
    MkCenterAlignedTopAppBar(
        title = {
            MkText(text = state.name.ifEmpty { "TemplateFeature" })
        },
        navigationIcon = {
            MkIconButton(
                onClick = { mainGraph?.safePop() },
            ) {
                MkIcon(Icons.AutoMirrored.Filled.ArrowBack)
            }
        },
        actions = {
            MkIconButton(
                onClick = { onEvent(TemplateFeatureEvent.Refresh) },
            ) {
                MkIcon(Icons.Default.Refresh)
            }
        },
        scrollBehavior = scrollBehavior,
    )
}
