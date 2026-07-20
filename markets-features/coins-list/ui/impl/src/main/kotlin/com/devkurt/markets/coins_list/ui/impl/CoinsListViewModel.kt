package com.devkurt.markets.coins_list.ui.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devkurt.markets.coins_list.domain.api.model.Coin
import com.devkurt.markets.coins_list.domain.api.usecase.CoinsListUseCase
import com.devkurt.markets.watchlist.domain.api.usecase.FlowWatchlistIdsUseCase
import com.devkurt.markets.watchlist.domain.api.usecase.ToggleWatchlistUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CoinsListViewModel(
    coinsListUseCase: CoinsListUseCase,
    flowWatchlistIdsUseCase: FlowWatchlistIdsUseCase,
    private val toggleWatchlistUseCase: ToggleWatchlistUseCase,
) : ViewModel() {

    val coins: Flow<PagingData<Coin>> = coinsListUseCase().cachedIn(viewModelScope)

    val state: StateFlow<CoinsListState> = flowWatchlistIdsUseCase()
        .map { ids -> CoinsListState(watchlistIds = ids) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = CoinsListState(),
        )

    fun onEvent(event: CoinsListEvent) {
        when (event) {
            is CoinsListEvent.WatchlistToggled -> viewModelScope.launch {
                toggleWatchlistUseCase(event.coinId)
            }
        }
    }
}
