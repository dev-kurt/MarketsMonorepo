package com.devkurt.markets.navigation.api

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

interface GraphEntryProvider<T : NavKey> {
    fun EntryProviderScope<T>.install()
}
