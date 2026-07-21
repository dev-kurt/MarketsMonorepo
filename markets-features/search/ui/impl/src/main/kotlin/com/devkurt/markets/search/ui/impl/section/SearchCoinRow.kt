package com.devkurt.markets.search.ui.impl.section

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devkurt.markets.search.domain.api.model.SearchCoin
import com.devkurt.markets.search.ui.impl.R
import com.devkurt.markets.ui.api.display.MkAsyncImage
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.layout.MkCard
import com.devkurt.markets.ui.api.testing.mkTestTag
import com.devkurt.markets.ui.api.theme.MkTheme

@Composable
fun SearchCoinRow(
    coin: SearchCoin,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MkCard(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .mkTestTag("search_coin_row"),
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

            coin.marketCapRank?.let { rank ->
                MkText(
                    text = stringResource(R.string.search_rank, rank),
                    color = MkTheme.colorScheme.onSurfaceVariant,
                    style = MkTheme.typography.label,
                )
            }
        }
    }
}
