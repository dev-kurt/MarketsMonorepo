package com.devkurt.markets.dev_tools.ui.impl

sealed interface DevToolsEffect {
    data class ShowMessage(val message: String) : DevToolsEffect
}
