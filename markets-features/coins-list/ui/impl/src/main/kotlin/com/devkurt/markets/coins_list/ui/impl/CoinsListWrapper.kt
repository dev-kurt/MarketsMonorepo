package com.devkurt.markets.coins_list.ui.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import org.koin.androidx.compose.koinViewModel

@Composable
fun CoinsListWrapper(
    viewModel: CoinsListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val coins = viewModel.coins.collectAsLazyPagingItems()

    CoinsListScreen(
        state = state,
        coins = coins,
        onEvent = viewModel::onEvent,
    )
}
