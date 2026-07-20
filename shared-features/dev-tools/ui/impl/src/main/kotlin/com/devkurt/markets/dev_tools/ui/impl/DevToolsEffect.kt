package com.devkurt.markets.dev_tools.ui.impl

import androidx.annotation.StringRes

sealed interface DevToolsEffect {
    data class ShowMessage(@StringRes val messageRes: Int) : DevToolsEffect
}
