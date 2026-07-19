package com.devkurt.markets.coins_list.ui.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun CoinsListWrapper(
    viewModel: CoinsListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    CoinsListScreen(
        state = state,
        onEvent = onEvent,
    )
}
