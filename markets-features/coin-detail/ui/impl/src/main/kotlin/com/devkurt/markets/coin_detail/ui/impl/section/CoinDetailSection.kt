package com.devkurt.markets.coin_detail.ui.impl.section

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devkurt.markets.coin_detail.ui.impl.R
import com.devkurt.markets.coin_detail.ui.impl.model.CoinDetailUi
import com.devkurt.markets.ui.api.display.MkAsyncImage
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.layout.MkCard
import com.devkurt.markets.ui.api.theme.MkTheme

@Composable
fun CoinDetailSection(
    coin: CoinDetailUi,
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
                    text = stringResource(R.string.coin_detail_rank, coin.marketCapRank),
                    color = MkTheme.colorScheme.onSurfaceVariant,
                    style = MkTheme.typography.label,
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                MkText(text = coin.formattedPrice, style = MkTheme.typography.titleMedium)
                MkText(
                    text = coin.formattedChange,
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
                CoinDetailStatRow(
                    stringResource(R.string.coin_detail_market_cap),
                    coin.formattedMarketCap
                )
                CoinDetailStatRow(
                    stringResource(R.string.coin_detail_volume_24h),
                    coin.formattedTotalVolume
                )
                CoinDetailStatRow(
                    stringResource(R.string.coin_detail_high_24h),
                    coin.formattedHigh24h
                )
                CoinDetailStatRow(
                    stringResource(R.string.coin_detail_low_24h),
                    coin.formattedLow24h
                )
                CoinDetailStatRow(
                    stringResource(R.string.coin_detail_all_time_high),
                    coin.formattedAllTimeHigh
                )
                CoinDetailStatRow(
                    stringResource(R.string.coin_detail_all_time_low),
                    coin.formattedAllTimeLow
                )
                CoinDetailStatRow(
                    stringResource(R.string.coin_detail_circulating_supply),
                    coin.formattedCirculatingSupply
                )
                CoinDetailStatRow(
                    label = stringResource(R.string.coin_detail_max_supply),
                    value = coin.formattedMaxSupply
                        ?: stringResource(R.string.coin_detail_max_supply_unlimited),
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
