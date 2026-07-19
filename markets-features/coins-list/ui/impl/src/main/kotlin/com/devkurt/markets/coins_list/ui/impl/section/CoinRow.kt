package com.devkurt.markets.coins_list.ui.impl.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.devkurt.markets.coins_list.domain.api.model.Coin
import com.devkurt.markets.ui.api.display.MkAsyncImage
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.layout.MkCard
import com.devkurt.markets.ui.api.theme.MkTheme

@Composable
fun CoinRow(
    coin: Coin,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MkCard(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MkTheme.padding.md),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MkTheme.padding.md),
        ) {
            MkText(
                text = coin.marketCapRank.toString(),
                color = MkTheme.colorScheme.onSurfaceVariant,
                style = MkTheme.typography.label,
                textAlign = TextAlign.End,
                modifier = Modifier.size(width = 24.dp, height = 20.dp),
            )

            MkAsyncImage(
                model = coin.imageUrl,
                contentDescription = coin.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape),
            )

            Column(modifier = Modifier.weight(1f)) {
                MkText(text = coin.name, maxLines = 1)
                MkText(
                    text = coin.symbol,
                    color = MkTheme.colorScheme.onSurfaceVariant,
                    style = MkTheme.typography.label,
                    maxLines = 1,
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                MkText(text = coin.formattedPrice(), maxLines = 1)
                MkText(
                    text = coin.formattedChange(),
                    color = if (coin.isPriceUp) {
                        MkTheme.colorScheme.success
                    } else {
                        MkTheme.colorScheme.error
                    },
                    style = MkTheme.typography.label,
                    maxLines = 1,
                )
            }
        }
    }
}
