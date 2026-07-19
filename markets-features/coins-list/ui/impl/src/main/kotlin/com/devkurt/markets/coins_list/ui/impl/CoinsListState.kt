package com.devkurt.markets.coins_list.ui.impl

import androidx.compose.runtime.Immutable

@Immutable
data class CoinsListState(
    val isLoading: Boolean = false,
    val name: String = "",
    val error: String? = null,
)
