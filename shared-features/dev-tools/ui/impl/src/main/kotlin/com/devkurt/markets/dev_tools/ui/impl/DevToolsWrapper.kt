package com.devkurt.markets.dev_tools.ui.impl

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun DevToolsWrapper(
    viewModel: DevToolsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val resources = LocalResources.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is DevToolsEffect.ShowMessage ->
                    Toast.makeText(
                        context,
                        resources.getString(effect.messageRes),
                        Toast.LENGTH_SHORT
                    )
                        .show()
            }
        }
    }

    DevToolsScreen(state = state, onEvent = viewModel::onEvent)
}
