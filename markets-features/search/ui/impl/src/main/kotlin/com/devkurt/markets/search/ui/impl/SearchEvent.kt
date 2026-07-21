package com.devkurt.markets.search.ui.impl

sealed interface SearchEvent {
    data class QueryChanged(val query: String) : SearchEvent
    data object Retry : SearchEvent
}
