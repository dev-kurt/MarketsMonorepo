package com.devkurt.markets.graph_list.ui.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import com.devkurt.markets.navigation.api.GraphMarkets

interface GraphList : GraphMarkets

object LocalGraphList {
    private val local = compositionLocalOf<NavBackStack<GraphList>?> { null }

    val current: NavBackStack<GraphList>
        @Composable
        get() = local.current ?: error("No GraphList navigation provided")

    val currentOrNull: NavBackStack<GraphList>?
        @Composable
        get() = local.current

    infix fun provides(
        navBackStack: NavBackStack<GraphList>,
    ): ProvidedValue<NavBackStack<GraphList>?> = local.provides(navBackStack)
}
