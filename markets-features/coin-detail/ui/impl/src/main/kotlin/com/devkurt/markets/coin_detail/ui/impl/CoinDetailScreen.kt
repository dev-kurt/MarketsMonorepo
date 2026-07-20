package com.devkurt.markets.coin_detail.ui.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.devkurt.markets.coin_detail.domain.api.model.CoinDetail
import com.devkurt.markets.coin_detail.ui.impl.section.CoinDetailStatRow
import com.devkurt.markets.coin_detail.ui.impl.section.asChangePercent
import com.devkurt.markets.coin_detail.ui.impl.section.asCompactAmount
import com.devkurt.markets.coin_detail.ui.impl.section.asPrice
import com.devkurt.markets.coin_detail.ui.impl.section.asSupply
import com.devkurt.markets.ui.api.bars.MkCenterAlignedTopAppBar
import com.devkurt.markets.ui.api.buttons.MkIconButton
import com.devkurt.markets.ui.api.buttons.MkTextButton
import com.devkurt.markets.ui.api.display.MkAsyncImage
import com.devkurt.markets.ui.api.display.MkIcon
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.feedback.MkError
import com.devkurt.markets.ui.api.feedback.MkLoading
import com.devkurt.markets.ui.api.frame.MkScreenScaffold
import com.devkurt.markets.ui.api.layout.MkCard
import com.devkurt.markets.ui.api.theme.MkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailScreen(
    state: CoinDetailState,
    onEvent: (CoinDetailEvent) -> Unit,
    onBack: () -> Unit,
) {
    MkScreenScaffold(
        topBar = {
            MkCenterAlignedTopAppBar(
                title = { MkText(state.coin?.name.orEmpty()) },
                navigationIcon = {
                    MkIconButton(onClick = onBack) {
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
                            MkText("Retry")
                        }
                    },
                )
            }

            coin == null -> MkLoading(modifier = Modifier.padding(paddingValues))

            else -> CoinDetailContent(
                coin = coin,
                modifier = Modifier.padding(paddingValues),
            )
        }
    }
}

@Composable
private fun CoinDetailContent(
    coin: CoinDetail,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(MkTheme.padding.md),
        verticalArrangement = Arrangement.spacedBy(MkTheme.padding.md),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MkTheme.padding.md),
        ) {
            MkAsyncImage(
                model = coin.imageUrl,
                contentDescription = coin.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape),
            )
            Column(modifier = Modifier.weight(1f)) {
                MkText(text = coin.symbol, style = MkTheme.typography.titleMedium)
                MkText(
                    text = "Rank #${coin.marketCapRank}",
                    color = MkTheme.colorScheme.onSurfaceVariant,
                    style = MkTheme.typography.label,
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                MkText(text = coin.price.asPrice(), style = MkTheme.typography.titleMedium)
                MkText(
                    text = coin.changePercent24h.asChangePercent(),
                    color = if (coin.isPriceUp) {
                        MkTheme.colorScheme.success
                    } else {
                        MkTheme.colorScheme.error
                    },
                    style = MkTheme.typography.label,
                )
            }
        }

        MkCard {
            Column(
                modifier = Modifier.padding(MkTheme.padding.md),
                verticalArrangement = Arrangement.spacedBy(MkTheme.padding.sm),
            ) {
                CoinDetailStatRow("Market cap", coin.marketCap.asCompactAmount())
                CoinDetailStatRow("24h volume", coin.totalVolume.asCompactAmount())
                CoinDetailStatRow("24h high", coin.high24h.asPrice())
                CoinDetailStatRow("24h low", coin.low24h.asPrice())
                CoinDetailStatRow("All-time high", coin.allTimeHigh.asPrice())
                CoinDetailStatRow("All-time low", coin.allTimeLow.asPrice())
                CoinDetailStatRow("Circulating supply", coin.circulatingSupply.asSupply())
                CoinDetailStatRow(
                    label = "Max supply",
                    value = coin.maxSupply?.asSupply() ?: "Unlimited",
                )
            }
        }

        if (coin.description.isNotBlank()) {
            MkText(
                text = coin.description,
                color = MkTheme.colorScheme.onSurfaceVariant,
                style = MkTheme.typography.label,
            )
        }
    }
}
