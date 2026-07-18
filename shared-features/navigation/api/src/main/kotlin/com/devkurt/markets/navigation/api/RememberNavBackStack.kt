package com.devkurt.markets.navigation.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.serialization.NavBackStackSerializer
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.modules.SerializersModule

@Composable
inline fun <reified T : NavKey> rememberNavBackStack(
    serializersModule: SerializersModule,
    vararg elements: T,
): NavBackStack<T> {
    val configuration = SavedStateConfiguration { this.serializersModule = serializersModule }
    return rememberSerializable(
        serializer = NavBackStackSerializer(PolymorphicSerializer(T::class)),
        configuration = configuration,
    ) {
        NavBackStack(*elements)
    }
}
