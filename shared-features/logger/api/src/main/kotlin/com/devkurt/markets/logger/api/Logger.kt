package com.devkurt.markets.logger.api

interface Logger {
    fun d(tag: String, message: () -> String)
    fun i(tag: String, message: () -> String)
    fun w(tag: String, throwable: Throwable? = null, message: () -> String)
    fun e(tag: String, throwable: Throwable? = null, message: () -> String)
}
