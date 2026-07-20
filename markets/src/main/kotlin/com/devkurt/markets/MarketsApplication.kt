package com.devkurt.markets

import android.app.Application
import com.devkurt.markets.graph_main.di.StartKoin

class MarketsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        StartKoin.doInitKoin(this)
    }
}
