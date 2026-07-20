package com.devkurt.markets.coins_list.ui.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devkurt.markets.coins_list.domain.api.model.Coin
import com.devkurt.markets.coins_list.domain.api.repository.CoinsListRepository
import com.devkurt.markets.watchlist.domain.api.repository.WatchlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CoinsListViewModel(
    coinsListRepository: CoinsListRepository,
    private val watchlistRepository: WatchlistRepository,
) : ViewModel() {

    val coins: Flow<PagingData<Coin>> = coinsListRepository.getCoins().cachedIn(viewModelScope)

    val state: StateFlow<CoinsListState> = watchlistRepository.flowWatchlistIds()
        .map { ids -> CoinsListState(watchlistIds = ids) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = CoinsListState(),
        )

    fun onEvent(event: CoinsListEvent) {
        when (event) {
            is CoinsListEvent.WatchlistToggled -> viewModelScope.launch {
                watchlistRepository.toggle(event.coinId)
            }
        }
    }
}
