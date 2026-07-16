package com.devkurt.markets.navigation.di

import com.devkurt.markets.navigation.api.SignalBus
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
class NavigationModule {
    @Single
    fun signalBus(): SignalBus = SignalBus()
}
