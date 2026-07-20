package com.devkurt.markets.dev_tools.ui.api

import androidx.annotation.StringRes

interface DevToolsAction {
    @get:StringRes
    val titleRes: Int

    @get:StringRes
    val successMessageRes: Int

    suspend fun execute(): Result<Unit>
}
