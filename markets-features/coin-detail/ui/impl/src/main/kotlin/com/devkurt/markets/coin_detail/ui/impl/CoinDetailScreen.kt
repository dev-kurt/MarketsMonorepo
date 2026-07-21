package com.devkurt.markets.coin_detail.ui.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devkurt.markets.coin_detail.ui.impl.section.CoinDetailSection
import com.devkurt.markets.coin_detail.ui.impl.section.CoinDetailTopBar
import com.devkurt.markets.ui.api.feedback.MkFeedbackPlaceholder
import com.devkurt.markets.ui.api.feedback.MkFeedbackType
import com.devkurt.markets.ui.api.feedback.MkLoading
import com.devkurt.markets.ui.api.frame.MkScreenScaffold
import com.devkurt.markets.ui.api.theme.MkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailScreen(
    state: CoinDetailState,
    onEvent: (CoinDetailEvent) -> Unit,
) {
    MkScreenScaffold(
        topBar = { CoinDetailTopBar(state) },
    ) { paddingValues ->
        val coin = state.coin

        when {
            state.error != null && coin == null -> {
                MkFeedbackPlaceholder(
                    message = state.error,
                    type = MkFeedbackType.Error,
                    onRetry = { onEvent(CoinDetailEvent.Retry) },
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(MkTheme.padding.md),
                )
            }

            coin == null -> {
                MkLoading(modifier = Modifier.padding(paddingValues))
            }

            else -> {
                CoinDetailSection(
                    coin = coin,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }
    }
}
