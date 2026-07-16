package com.devkurt.markets.navigation.api

import androidx.navigation3.runtime.EntryProviderScope

fun interface GraphMainRoutes : GraphEntryProvider<GraphMain> {
    override fun EntryProviderScope<GraphMain>.install() {
        invoke(this)
    }

    fun invoke(scope: EntryProviderScope<GraphMain>)
}
