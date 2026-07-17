package com.devkurt.markets.graph_bottom.di

import com.devkurt.markets.graph_bottom.ui.api.GraphBottom
import com.devkurt.markets.graph_bottom.ui.api.GraphBottomRoute
import com.devkurt.markets.graph_bottom.ui.impl.GraphBottomScreen
import com.devkurt.markets.graph_bottom.ui.impl.GraphBottomViewModel
import com.devkurt.markets.navigation.api.GraphEntryProvider
import com.devkurt.markets.navigation.api.GraphMainRoutes
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@Configuration
class GraphBottomModule {
    @Single(binds = [GraphEntryProvider::class])
    @Named("graphBottomRoutes")
    fun graphBottomRoutes(): GraphMainRoutes = GraphMainRoutes { scope ->
        scope.entry<GraphBottomRoute> {
            GraphBottomScreen()
        }
    }

    @KoinViewModel
    fun graphBottomViewModel(
        entryProviders: List<GraphEntryProvider<GraphBottom>>,
    ): GraphBottomViewModel = GraphBottomViewModel(
        entryProviders = entryProviders,
    )
}
