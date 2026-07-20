package com.devkurt.markets.dev_tools.ui.impl

import androidx.compose.runtime.Immutable

@Immutable
data class DevToolsState(
    val isLoading: Boolean = false,
    val actionTitles: List<String> = emptyList(),
)
