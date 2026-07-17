package com.devkurt.markets.graph_main.di

import android.content.Context
import coil3.SingletonImageLoader
import org.koin.android.ext.koin.androidContext
import org.koin.mp.KoinPlatform
import org.koin.plugin.module.dsl.startKoin

object StartKoin {
    fun doInitKoin(context: Context) {
        startKoin<MarketsKoinApp> {
            androidContext(context)
        }

        SingletonImageLoader.setSafe { KoinPlatform.getKoin().get() }
    }
}
