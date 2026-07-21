package com.devkurt.markets.logger.api

interface Logger {
    fun debug(tag: String, message: () -> String)
    fun info(tag: String, message: () -> String)
    fun warn(tag: String, throwable: Throwable? = null, message: () -> String)
    fun error(tag: String, throwable: Throwable? = null, message: () -> String)
}
