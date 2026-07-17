package com.devkurt.markets.graph_main.di

import com.devkurt.markets.graph_main.ui.impl.GraphMainViewModel
import com.devkurt.markets.navigation.api.GraphEntryProvider
import com.devkurt.markets.navigation.api.GraphMain
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Module

@Module
@Configuration
class GraphMainModule {
    @KoinViewModel
    fun graphMainViewModel(
        entryProviders: List<GraphEntryProvider<GraphMain>>,
    ): GraphMainViewModel = GraphMainViewModel(
        entryProviders = entryProviders,
    )
}
