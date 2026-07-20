package com.devkurt.markets.ui.api.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.devkurt.markets.ui.api.R
import com.devkurt.markets.ui.api.buttons.MkTextButton
import com.devkurt.markets.ui.api.display.MkIcon
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.theme.MkTheme

@Composable
fun MkFeedbackPlaceholder(
    message: String,
    modifier: Modifier = Modifier,
    type: MkFeedbackType = MkFeedbackType.Error,
    onRetry: (() -> Unit)? = null,
) {
    val accent = when (type) {
        MkFeedbackType.Error -> MkTheme.colorScheme.error
        MkFeedbackType.Warning -> MkTheme.colorScheme.warning
        MkFeedbackType.Info -> MkTheme.colorScheme.info
        MkFeedbackType.Success -> MkTheme.colorScheme.success
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MkTheme.shapes.medium)
            .background(MkTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            .padding(MkTheme.padding.md),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MkTheme.padding.sm),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MkTheme.padding.sm),
        ) {
            MkIcon(imageVector = type.icon, tint = accent)
            MkText(
                text = stringResource(type.labelRes),
                color = accent,
                style = MkTheme.typography.titleMedium,
            )
        }

        MkText(
            text = message,
            color = MkTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )

        if (onRetry != null) {
            MkTextButton(onClick = onRetry) {
                MkText(text = stringResource(R.string.mk_retry))
            }
        }
    }
}
