package com.devkurt.markets.dev_tools.di

import com.devkurt.markets.dev_tools.ui.api.DevToolsAction
import com.devkurt.markets.dev_tools.ui.api.DevToolsRoute
import com.devkurt.markets.dev_tools.ui.impl.DevToolsGraphContent
import com.devkurt.markets.dev_tools.ui.impl.DevToolsViewModel
import com.devkurt.markets.dev_tools.ui.impl.DevToolsWrapper
import com.devkurt.markets.navigation.api.GraphMain
import com.devkurt.markets.navigation.api.GraphMainContent
import com.devkurt.markets.navigation.api.GraphMainRoutes
import com.devkurt.markets.serialization.api.MkSerializersModule
import com.devkurt.markets.ui.api.navigation.MkBottomSheetSceneStrategy
import kotlinx.serialization.modules.polymorphic
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@Configuration
class DevToolsModule {
    @Single
    @Named("devToolsRouteSerializers")
    fun devToolsRouteSerializers(): MkSerializersModule = MkSerializersModule {
        polymorphic(GraphMain::class) {
            subclass(DevToolsRoute::class, DevToolsRoute.serializer())
        }
    }

    @Single
    @Named("devToolsRoutes")
    fun devToolsRoutes(): GraphMainRoutes = GraphMainRoutes { scope ->
        scope.entry<DevToolsRoute>(
            metadata = MkBottomSheetSceneStrategy.bottomSheet(),
        ) {
            DevToolsWrapper()
        }
    }

    @Single
    @Named("devToolsGraphContent")
    fun devToolsGraphContent(): GraphMainContent = DevToolsGraphContent()

    @KoinViewModel
    fun devToolsViewModel(
        actions: List<DevToolsAction>,
    ): DevToolsViewModel = DevToolsViewModel(actions = actions)
}
