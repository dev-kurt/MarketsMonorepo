package com.devkurt.markets.ui.api.inputs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester

@Stable
class MkSearchBarState internal constructor(initialQuery: String) {

    var query by mutableStateOf(initialQuery)

    val focusRequester = FocusRequester()

    fun clear() {
        query = ""
    }

    companion object {
        val Saver: Saver<MkSearchBarState, String> = Saver(
            save = { state -> state.query },
            restore = { query -> MkSearchBarState(query) },
        )
    }
}

@Composable
fun rememberMkSearchBarState(initialQuery: String = ""): MkSearchBarState =
    rememberSaveable(saver = MkSearchBarState.Saver) { MkSearchBarState(initialQuery) }
