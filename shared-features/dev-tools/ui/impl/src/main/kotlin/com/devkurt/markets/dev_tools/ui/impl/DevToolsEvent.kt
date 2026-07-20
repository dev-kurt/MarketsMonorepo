package com.devkurt.markets.dev_tools.ui.impl

sealed interface DevToolsEvent {
    data class ExecuteAction(val index: Int) : DevToolsEvent
}
