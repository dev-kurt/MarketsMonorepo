package com.devkurt.markets.logger.impl

import com.devkurt.markets.logger.api.Logger
import co.touchlab.kermit.Logger as Kermit

class KermitLogger(private val kermit: Kermit) : Logger {
    override fun d(tag: String, message: () -> String) =
        kermit.d(tag = tag, message = message)

    override fun i(tag: String, message: () -> String) =
        kermit.i(tag = tag, message = message)

    override fun w(tag: String, throwable: Throwable?, message: () -> String) =
        kermit.w(throwable = throwable, tag = tag, message = message)

    override fun e(tag: String, throwable: Throwable?, message: () -> String) =
        kermit.e(throwable = throwable, tag = tag, message = message)
}
