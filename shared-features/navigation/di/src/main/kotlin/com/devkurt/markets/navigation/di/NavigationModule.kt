package com.devkurt.markets.navigation.di

import com.devkurt.markets.navigation.api.RouteSerializers
import com.devkurt.markets.navigation.api.SignalBus
import kotlinx.serialization.modules.SerializersModule
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
class NavigationModule {
    @Single
    fun signalBus(): SignalBus = SignalBus()

    @Single
    fun routeSerializersModule(routeSerializers: List<RouteSerializers>): SerializersModule =
        SerializersModule {
            routeSerializers.forEach { serializers ->
                with(serializers) { register() }
            }
        }
}
