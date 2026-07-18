package com.devkurt.markets.serialization.api

import kotlinx.serialization.modules.SerializersModuleBuilder

fun interface MkSerializersModule {
    fun SerializersModuleBuilder.register()
}
