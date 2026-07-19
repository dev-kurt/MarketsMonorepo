package com.devkurt.markets.graph_list.ui.api

import androidx.navigation3.runtime.EntryProviderScope
import com.devkurt.markets.navigation.api.GraphEntryProvider

fun interface GraphListRoutes : GraphEntryProvider<GraphList> {
    override fun EntryProviderScope<GraphList>.install() {
        invoke(this)
    }

    fun invoke(scope: EntryProviderScope<GraphList>)
}
