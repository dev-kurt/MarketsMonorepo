package com.devkurt.markets.watchlist.ui.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devkurt.markets.ui.api.state.LoadingCounter
import com.devkurt.markets.watchlist.domain.api.repository.WatchlistRepository
import com.devkurt.markets.watchlist.ui.impl.mapper.toUi
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

class WatchlistViewModel(
    private val watchlistRepository: WatchlistRepository,
) : ViewModel() {

    private val loading = LoadingCounter()

    private val _state = MutableStateFlow(WatchlistState())
    val state: StateFlow<WatchlistState> = combine(
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

    fun onEvent(event: WatchlistEvent) {
        when (event) {
            is WatchlistEvent.RemoveClicked -> toggle(event.coinId)
            WatchlistEvent.Retry -> load(lastIds)
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
                            it.copy(coins = coins.map { coin -> coin.toUi() }, error = null)
                        }
                    }
                    .onFailure { throwable ->
                        _state.update { it.copy(error = throwable.message) }
                    }
            }
        }
    }

    private fun toggle(coinId: String) {
        viewModelScope.launch {
            watchlistRepository.toggle(coinId)
        }
    }
}
