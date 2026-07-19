package com.devkurt.markets.coins_list.ui.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devkurt.markets.coins_list.ui.impl.section.CoinsListTopBar
import com.devkurt.markets.ui.api.buttons.MkTextButton
import com.devkurt.markets.ui.api.display.MkText
import com.devkurt.markets.ui.api.feedback.MkError
import com.devkurt.markets.ui.api.frame.MkScreenScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinsListScreen(
    state: CoinsListState,
    onEvent: (CoinsListEvent) -> Unit,
) {
    MkScreenScaffold(
        topBar = {
            CoinsListTopBar(state, onEvent)
        },
        isLoading = state.isLoading,
    ) { paddingValues ->
        state.error?.let { error ->
            MkError(
                message = error,
                action = {
                    MkTextButton(onClick = { onEvent(CoinsListEvent.Refresh) }) {
                        MkText("Retry")
                    }
                },
            )
            return@MkScreenScaffold
        }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            MkText(text = state.name)
        }
    }
}
