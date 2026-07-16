package com.devkurt.markets.ui.api.state

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class LoadingCounter {
    private val activeCount = MutableStateFlow(0)
    val isLoading: Flow<Boolean> = activeCount.map { it > 0 }.distinctUntilChanged()

    suspend fun <T> withLoading(block: suspend () -> T): T {
        activeCount.update { it + 1 }
        return try {
            block()
        } finally {
            activeCount.update { it - 1 }
        }
    }
}
