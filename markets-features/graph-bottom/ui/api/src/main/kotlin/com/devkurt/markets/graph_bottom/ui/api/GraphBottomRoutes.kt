package com.devkurt.markets.graph_bottom.ui.api

import androidx.navigation3.runtime.EntryProviderScope
import com.devkurt.markets.navigation.api.GraphEntryProvider

fun interface GraphBottomRoutes : GraphEntryProvider<GraphBottom> {
    override fun EntryProviderScope<GraphBottom>.install() {
        invoke(this)
    }

    fun invoke(scope: EntryProviderScope<GraphBottom>)
}
