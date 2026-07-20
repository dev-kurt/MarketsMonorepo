package com.devkurt.markets.dev_tools.ui.impl

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
data class DevToolsState(
    val isLoading: Boolean = false,
    @StringRes val actionTitleResIds: List<Int> = emptyList(),
)
