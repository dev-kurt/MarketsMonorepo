package com.devkurt.markets.ui.api.frame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.devkurt.markets.ui.api.feedback.MkLinearProgressIndicator
import com.devkurt.markets.ui.api.feedback.MkPullToRefreshBox
import com.devkurt.markets.ui.api.theme.MkTheme

@Composable
fun MkScreenScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    isLoading: Boolean = false,
    progress: (() -> Float)? = null,
    onRefresh: (() -> Unit)? = null,
    containerColor: Color = MkTheme.colorScheme.background,
    contentWindowInsets: WindowInsets = WindowInsets(0, 0, 0, 0),
    content: @Composable (PaddingValues) -> Unit,
) {
    val isActiveLoading = isLoading || progress != null
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Column {
                topBar()
                if (isActiveLoading) {
                    if (progress != null) {
                        MkLinearProgressIndicator(
                            progress = progress,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    } else {
                        MkLinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        },
        bottomBar = bottomBar,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentWindowInsets = contentWindowInsets,
        content = { paddingValues ->
            if (onRefresh != null) {
                MkPullToRefreshBox(
                    isRefreshing = isLoading,
                    onRefresh = onRefresh,
                ) {
                    content(paddingValues)
                }
            } else {
                content(paddingValues)
            }
        },
    )
}
