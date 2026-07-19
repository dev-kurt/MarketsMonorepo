package com.devkurt.markets.graph_main.di

import android.content.Context
import coil3.SingletonImageLoader
import com.devkurt.markets.network.api.NetworkConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.mp.KoinPlatform
import org.koin.plugin.module.dsl.startKoin

object StartKoin {
    fun doInitKoin(context: Context, networkConfig: NetworkConfig) {
        startKoin<MarketsKoinApp> {
            androidContext(context)
            modules(
                module {
                    single { networkConfig }
                },
            )
        }

        SingletonImageLoader.setSafe { KoinPlatform.getKoin().get() }
    }
}
