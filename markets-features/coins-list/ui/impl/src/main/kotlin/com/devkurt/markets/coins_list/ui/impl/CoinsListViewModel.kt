package com.devkurt.markets.coins_list.ui.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devkurt.markets.coins_list.domain.api.model.Coin
import com.devkurt.markets.coins_list.domain.api.usecase.CoinsListUseCase
import kotlinx.coroutines.flow.Flow

class CoinsListViewModel(
    coinsListUseCase: CoinsListUseCase,
) : ViewModel() {

    val coins: Flow<PagingData<Coin>> = coinsListUseCase().cachedIn(viewModelScope)
}
