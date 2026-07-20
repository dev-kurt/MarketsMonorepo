package com.devkurt.markets.coins_list.ui.impl

import androidx.compose.runtime.Immutable

@Immutable
data class CoinsListState(
    val watchlistIds: Set<String> = emptySet(),
)
