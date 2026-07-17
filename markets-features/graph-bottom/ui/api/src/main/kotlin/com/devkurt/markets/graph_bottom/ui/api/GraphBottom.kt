package com.devkurt.markets.graph_bottom.ui.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import com.devkurt.markets.navigation.api.GraphMarkets

interface GraphBottom : GraphMarkets

object LocalGraphBottom {
    private val local = compositionLocalOf<NavBackStack<GraphBottom>?> { null }

    val current: NavBackStack<GraphBottom>
        @Composable
        get() = local.current ?: error("No GraphBottom navigation provided")

    val currentOrNull: NavBackStack<GraphBottom>?
        @Composable
        get() = local.current

    infix fun provides(
        navBackStack: NavBackStack<GraphBottom>,
    ): ProvidedValue<NavBackStack<GraphBottom>?> = local.provides(navBackStack)
}
