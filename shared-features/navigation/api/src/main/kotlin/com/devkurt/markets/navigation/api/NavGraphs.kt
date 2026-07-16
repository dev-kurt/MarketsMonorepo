package com.devkurt.markets.navigation.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

interface GraphMarkets : NavKey

interface GraphMain : GraphMarkets

object LocalGraphMain {
    private val local = compositionLocalOf<NavBackStack<GraphMain>?> { null }

    val current: NavBackStack<GraphMain>
        @Composable
        get() = local.current ?: error("No GraphMain navigation provided")

    val currentOrNull: NavBackStack<GraphMain>?
        @Composable
        get() = local.current

    infix fun provides(
        navBackStack: NavBackStack<GraphMain>,
    ): ProvidedValue<NavBackStack<GraphMain>?> = local.provides(navBackStack)
}
