package com.devkurt.markets.coin_detail.ui.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.devkurt.markets.coin_detail.ui.impl.section.CoinDetailSection
import com.devkurt.markets.coin_detail.ui.impl.section.CoinDetailTopBar
import com.devkurt.markets.ui.api.buttons.MkTextButton
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.feedback.MkError
import com.devkurt.markets.ui.api.feedback.MkLoading
import com.devkurt.markets.ui.api.frame.MkScreenScaffold
import com.devkurt.markets.ui.api.R as UiR

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
                MkError(
                    message = state.error,
                    action = {
                        MkTextButton(onClick = { onEvent(CoinDetailEvent.Retry) }) {
                            MkText(stringResource(UiR.string.mk_retry))
                        }
                    },
                )
            }

            coin == null -> MkLoading(modifier = Modifier.padding(paddingValues))

            else -> CoinDetailSection(
                coin = coin,
                modifier = Modifier.padding(paddingValues),
            )
        }
    }
}
