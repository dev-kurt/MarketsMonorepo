package com.devkurt.markets.dev_tools.ui.api

interface DevToolsAction {
    val title: String

    suspend fun execute(): Result<String>
}
