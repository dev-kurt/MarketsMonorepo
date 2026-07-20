package com.devkurt.markets.coin_detail.ui.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CoinDetailWrapper(
    coinId: String,
    onBack: () -> Unit,
    viewModel: CoinDetailViewModel = koinViewModel { parametersOf(coinId) },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CoinDetailScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onBack = onBack,
    )
}
