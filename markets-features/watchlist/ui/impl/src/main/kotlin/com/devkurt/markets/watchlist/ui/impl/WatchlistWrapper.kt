package com.devkurt.markets.watchlist.ui.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun WatchlistWrapper(
    viewModel: WatchlistViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    WatchlistScreen(
        state = state,
        onEvent = viewModel::onEvent,
    )
}
