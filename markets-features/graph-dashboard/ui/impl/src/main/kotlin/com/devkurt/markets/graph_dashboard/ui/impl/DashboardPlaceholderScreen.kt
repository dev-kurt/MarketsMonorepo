package com.devkurt.markets.graph_dashboard.ui.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.devkurt.markets.ui.api.bars.MkCenterAlignedTopAppBar
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.frame.MkScreenScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardPlaceholderScreen() {
    MkScreenScaffold(
        topBar = {
            MkCenterAlignedTopAppBar(
                title = { MkText("Markets") },
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            MkText("Coins coming soon")
        }
    }
}
