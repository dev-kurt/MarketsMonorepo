package com.devkurt.markets.serialization.api

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
object MkJson {

    val instance: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
        explicitNulls = false
        decodeEnumsCaseInsensitive = true
        allowSpecialFloatingPointValues = true
        allowTrailingComma = true
        allowComments = true
        encodeDefaults = true
        prettyPrint = true
        prettyPrintIndent = "    "
    }
}
