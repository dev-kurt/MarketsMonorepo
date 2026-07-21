package com.devkurt.markets.ui.api.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import com.devkurt.markets.ui.api.buttons.MkIconButton
import com.devkurt.markets.ui.api.display.LocalMkTextMaxLines
import com.devkurt.markets.ui.api.display.MkIcon
import com.devkurt.markets.ui.api.theme.MkTheme

@Composable
fun MkSearchBar(
    state: MkSearchBarState,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable () -> Unit = {
        MkIcon(
            imageVector = Icons.Default.Search,
            tint = MkTheme.colorScheme.onSurfaceVariant,
        )
    },
    trailingIcon: @Composable () -> Unit = {
        if (state.query.isNotEmpty()) {
            MkIconButton(onClick = { state.clear() }) {
                MkIcon(
                    imageVector = Icons.Default.Clear,
                    tint = MkTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    },
) {
    CompositionLocalProvider(LocalMkTextMaxLines provides 1) {
        OutlinedTextField(
            value = state.query,
            onValueChange = { value -> state.query = value },
            modifier = modifier
                .fillMaxWidth()
                .focusRequester(state.focusRequester),
            placeholder = placeholder,
            leadingIcon = { leadingIcon() },
            trailingIcon = { trailingIcon() },
            singleLine = true,
            shape = MkTheme.shapes.large,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MkTheme.colorScheme.primary,
                unfocusedBorderColor = MkTheme.colorScheme.outline,
            ),
        )
    }
}
