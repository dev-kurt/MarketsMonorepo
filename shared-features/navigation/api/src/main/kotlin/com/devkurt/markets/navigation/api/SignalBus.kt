package com.devkurt.markets.navigation.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

interface Signal

object LocalSignalBus {
    private val local: ProvidableCompositionLocal<SignalBus?> = compositionLocalOf { null }

    val current: SignalBus
        @Composable
        get() = local.current ?: error("No SignalBus has been provided")

    val currentOrNull: SignalBus?
        @Composable
        get() = local.current

    infix fun provides(bus: SignalBus): ProvidedValue<SignalBus?> = local.provides(bus)
}

@Composable
inline fun <reified T : Signal> SignalEffect(
    signalBus: SignalBus = LocalSignalBus.current,
    resultKey: String = T::class.toString(),
    listenerKey: String = resultKey,
    crossinline onSignal: suspend (T) -> Unit,
) {
    LaunchedEffect(key1 = resultKey, key2 = listenerKey) {
        signalBus.getSignalFlow<T>(resultKey, listenerKey)?.collect { result ->
            onSignal.invoke(result as T)
        }
    }
}

class SignalBus {

    val channelMap: MutableMap<String, MutableMap<String, Channel<Any?>>> = mutableMapOf()

    inline fun <reified T : Signal> getSignalFlow(
        resultKey: String = T::class.toString(),
        listenerKey: String = resultKey,
    ): Flow<Any?>? {
        val listenerMap = channelMap.getOrPut(resultKey) { mutableMapOf() }
        if (!listenerMap.contains(listenerKey)) {
            listenerMap[listenerKey] = Channel(
                capacity = BUFFERED,
                onBufferOverflow = BufferOverflow.SUSPEND,
            )
        }
        return listenerMap[listenerKey]?.receiveAsFlow()
    }

    inline fun <reified T : Signal> sendSignal(
        signal: T,
        resultKey: String = T::class.toString(),
    ) {
        val listenerMap = channelMap.getOrPut(resultKey) { mutableMapOf() }
        if (listenerMap.isEmpty()) {
            listenerMap[resultKey] = Channel<Any?>(
                capacity = BUFFERED,
                onBufferOverflow = BufferOverflow.SUSPEND,
            ).apply { trySend(signal) }
        } else {
            listenerMap.values.forEach { channel ->
                channel.trySend(signal)
            }
        }
    }

    inline fun <reified T : Signal> removeSignal(resultKey: String = T::class.toString()) {
        channelMap.remove(resultKey)
    }

    inline fun <reified T : Signal> removeListener(
        resultKey: String = T::class.toString(),
        listenerKey: String = resultKey,
    ) {
        channelMap[resultKey]?.remove(listenerKey)
    }
}
