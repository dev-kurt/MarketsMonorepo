package com.devkurt.markets.watchlist.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object WatchlistSerializer : Serializer<WatchlistData> {
    override val defaultValue: WatchlistData = WatchlistData()

    override suspend fun readFrom(input: InputStream): WatchlistData {
        return try {
            val text = input.readBytes().decodeToString()
            if (text.isBlank()) defaultValue else Json.decodeFromString(
                WatchlistData.serializer(),
                text
            )
        } catch (e: SerializationException) {
            throw CorruptionException("Watchlist JSON corrupted", e)
        }
    }

    override suspend fun writeTo(t: WatchlistData, output: OutputStream) {
        output.write(Json.encodeToString(WatchlistData.serializer(), t).encodeToByteArray())
    }
}
