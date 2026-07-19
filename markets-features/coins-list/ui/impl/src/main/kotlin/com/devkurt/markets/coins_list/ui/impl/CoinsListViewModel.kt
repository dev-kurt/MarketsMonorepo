package com.devkurt.markets.coins_list.ui.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devkurt.markets.coins_list.domain.api.usecase.CoinsListUseCase
import com.devkurt.markets.ui.api.state.LoadingCounter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinsListViewModel(
    private val coinsListUseCase: CoinsListUseCase,
) : ViewModel() {

    private val loading = LoadingCounter()

    private val _state = MutableStateFlow(CoinsListState())
    val state: StateFlow<CoinsListState> = combine(
        _state,
        loading.isLoading,
    ) { currentState, isLoading ->
        currentState.copy(
            isLoading = isLoading,
        )
    }.onStart {
        load()
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = _state.value,
    )

    fun onEvent(event: CoinsListEvent) {
        when (event) {
            is CoinsListEvent.UpdateState -> {
                _state.update { state -> event.update(state) }
            }

            CoinsListEvent.Refresh -> load()
        }
    }

    private fun load() {
        viewModelScope.launch {
            loading.withLoading {
                coinsListUseCase()
                    .onSuccess { feature ->
                        _state.update { it.copy(name = feature.name, error = null) }
                    }
                    .onFailure { throwable ->
                        _state.update { it.copy(error = throwable.message) }
                    }
            }
        }
    }
}
