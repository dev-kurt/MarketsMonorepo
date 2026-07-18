package com.devkurt.markets.navigation.api

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder

class RouteSerializers(val module: SerializersModule)

fun routeSerializers(builderAction: SerializersModuleBuilder.() -> Unit): RouteSerializers =
    RouteSerializers(SerializersModule(builderAction))
