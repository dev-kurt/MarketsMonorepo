package com.devkurt.markets.navigation.api

import kotlinx.serialization.modules.SerializersModuleBuilder

fun interface RouteSerializers {
    fun SerializersModuleBuilder.register()
}
