package com.devkurt.markets.coin_detail.ui.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.devkurt.markets.coin_detail.ui.impl.section.CoinDetailSection
import com.devkurt.markets.navigation.api.LocalGraphMain
import com.devkurt.markets.navigation.api.safePop
import com.devkurt.markets.ui.api.bars.MkCenterAlignedTopAppBar
import com.devkurt.markets.ui.api.buttons.MkIconButton
import com.devkurt.markets.ui.api.buttons.MkTextButton
import com.devkurt.markets.ui.api.display.MkIcon
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
    val mainGraph = LocalGraphMain.currentOrNull

    MkScreenScaffold(
        topBar = {
            MkCenterAlignedTopAppBar(
                title = { MkText(state.coin?.name.orEmpty()) },
                navigationIcon = {
                    MkIconButton(onClick = { mainGraph?.safePop() }) {
                        MkIcon(Icons.AutoMirrored.Filled.ArrowBack)
                    }
                },
            )
        },
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
