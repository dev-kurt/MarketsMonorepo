package com.devkurt.markets.dev_tools.ui.impl

import androidx.compose.runtime.Composable
import com.devkurt.markets.dev_tools.ui.api.DevToolsRoute
import com.devkurt.markets.navigation.api.GraphMainContent
import com.devkurt.markets.navigation.api.LocalGraphMain

class DevToolsGraphContent : GraphMainContent {
    @Composable
    override fun Content() {
        if (!rememberIsDebuggable()) return
        val graphMain = LocalGraphMain.currentOrNull ?: return
        ShakeEffect {
            if (graphMain.lastOrNull() != DevToolsRoute) {
                graphMain.add(DevToolsRoute)
            }
        }
    }
}
