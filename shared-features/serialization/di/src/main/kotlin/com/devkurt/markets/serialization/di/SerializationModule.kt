package com.devkurt.markets.serialization.di

import com.devkurt.markets.serialization.api.MkSerializersModule
import kotlinx.serialization.modules.SerializersModule
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
class SerializationModule {
    @Single
    fun serializersModule(serializersModules: List<MkSerializersModule>): SerializersModule =
        SerializersModule {
            serializersModules.forEach { module ->
                with(module) { register() }
            }
        }
}
