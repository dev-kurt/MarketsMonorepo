package com.devkurt.markets.coin_detail.ui.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devkurt.markets.coin_detail.domain.api.usecase.CoinDetailUseCase
import com.devkurt.markets.coin_detail.ui.api.CoinDetailRoute
import com.devkurt.markets.ui.api.state.LoadingCounter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinDetailViewModel(
    private val route: CoinDetailRoute,
    private val coinDetailUseCase: CoinDetailUseCase,
) : ViewModel() {

    private val loading = LoadingCounter()

    private val _state = MutableStateFlow(CoinDetailState(route = route))
    val state: StateFlow<CoinDetailState> = combine(
        _state,
        loading.isLoading,
    ) { currentState, isLoading ->
        currentState.copy(isLoading = isLoading)
    }.onStart {
        load()
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = _state.value,
    )

    fun onEvent(event: CoinDetailEvent) {
        when (event) {
            CoinDetailEvent.Retry -> load()
        }
    }

    private fun load() {
        viewModelScope.launch {
            loading.withLoading {
                coinDetailUseCase(route.coinId)
                    .onSuccess { coin ->
                        _state.update { it.copy(coin = coin, error = null) }
                    }
                    .onFailure { throwable ->
                        _state.update { it.copy(error = throwable.message) }
                    }
            }
        }
    }
}
