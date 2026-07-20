package com.devkurt.markets.dashboard.ui.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devkurt.markets.ui.api.state.LoadingCounter
import com.devkurt.markets.watchlist.domain.api.repository.WatchlistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TOP_COUNT = 10

class DashboardViewModel(
    private val watchlistRepository: WatchlistRepository,
) : ViewModel() {

    private val loading = LoadingCounter()

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = combine(
        _state,
        loading.isLoading,
    ) { currentState, isLoading ->
        currentState.copy(isLoading = isLoading)
    }.onStart {
        observeWatchlist()
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = _state.value,
    )

    private var lastIds: Set<String> = emptySet()

    fun onEvent(event: DashboardEvent) {
        when (event) {
            DashboardEvent.Retry -> load(lastIds)
        }
    }

    private fun observeWatchlist() {
        watchlistRepository.flowWatchlistIds()
            .onEach { ids ->
                lastIds = ids
                load(ids)
            }
            .launchIn(viewModelScope)
    }

    private fun load(ids: Set<String>) {
        viewModelScope.launch {
            loading.withLoading {
                watchlistRepository.getWatchlistCoins(ids)
                    .onSuccess { coins ->
                        _state.update {
                            it.copy(watchlistCoins = coins.take(TOP_COUNT), error = null)
                        }
                    }
                    .onFailure { throwable ->
                        _state.update { it.copy(error = throwable.message) }
                    }
            }
        }
    }
}
