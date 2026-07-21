package com.devkurt.markets.search.ui.impl

import androidx.compose.runtime.Immutable
import com.devkurt.markets.search.domain.api.model.SearchCoin

@Immutable
data class SearchState(
    val query: String = "",
    val results: List<SearchCoin> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
