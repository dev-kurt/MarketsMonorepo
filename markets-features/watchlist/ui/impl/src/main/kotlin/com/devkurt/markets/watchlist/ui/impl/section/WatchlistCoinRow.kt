package com.devkurt.markets.watchlist.ui.impl.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.devkurt.markets.ui.api.buttons.MkIconButton
import com.devkurt.markets.ui.api.display.MkAsyncImage
import com.devkurt.markets.ui.api.display.MkIcon
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.layout.MkCard
import com.devkurt.markets.ui.api.theme.MkTheme
import com.devkurt.markets.ui.api.testing.mkTestTag
import com.devkurt.markets.watchlist.domain.api.model.WatchlistCoin
import java.util.Locale

@Composable
fun WatchlistCoinRow(
    coin: WatchlistCoin,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onRemove: (() -> Unit)? = null,
) {
    MkCard(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .mkTestTag("watchlist_coin_row"),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MkTheme.padding.md),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MkTheme.padding.md),
        ) {
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

            if (onRemove != null) {
                MkIconButton(onClick = onRemove) {
                    MkIcon(
                        imageVector = Icons.Default.Clear,
                        tint = MkTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

fun WatchlistCoin.formattedPrice(): String {
    val pattern = if (price >= 1.0) "$%,.2f" else "$%,.6f"
    return String.format(Locale.US, pattern, price)
}

fun WatchlistCoin.formattedChange(): String =
    String.format(Locale.US, "%+.2f%%", changePercent24h)
