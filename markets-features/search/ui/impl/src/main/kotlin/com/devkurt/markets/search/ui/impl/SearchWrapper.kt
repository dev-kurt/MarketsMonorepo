package com.devkurt.markets.search.ui.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devkurt.markets.ui.api.inputs.rememberMkSearchBarState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchWrapper(
    viewModel: SearchViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchBarState = rememberMkSearchBarState()

    LaunchedEffect(searchBarState) {
        searchBarState.focusRequester.requestFocus()
        snapshotFlow { searchBarState.query }
            .collect { query -> viewModel.onEvent(SearchEvent.QueryChanged(query)) }
    }

    SearchScreen(
        state = state,
        searchBarState = searchBarState,
        onEvent = viewModel::onEvent,
    )
}
