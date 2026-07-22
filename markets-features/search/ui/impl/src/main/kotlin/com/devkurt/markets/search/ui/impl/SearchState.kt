package com.devkurt.markets.search.ui.impl

import androidx.compose.runtime.Immutable
import com.devkurt.markets.search.ui.impl.model.SearchCoinUi

@Immutable
data class SearchState(
    val query: String = "",
    val results: List<SearchCoinUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
