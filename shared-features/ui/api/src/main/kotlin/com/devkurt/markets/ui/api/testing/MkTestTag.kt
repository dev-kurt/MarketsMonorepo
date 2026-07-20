package com.devkurt.markets.ui.api.testing

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.mkTestTag(tag: String): Modifier =
    this
        .semantics { testTagsAsResourceId = true }
        .testTag(tag)
        .semantics { contentDescription = tag }
