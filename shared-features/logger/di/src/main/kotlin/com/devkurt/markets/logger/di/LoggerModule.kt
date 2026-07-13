package com.devkurt.markets.logger.di

import co.touchlab.kermit.Logger as Kermit
import co.touchlab.kermit.Severity
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import com.devkurt.markets.logger.api.Logger
import com.devkurt.markets.logger.impl.KermitLogger
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
class LoggerModule {
    @Single
    fun logger(): Logger = KermitLogger(
        Kermit(
            config = StaticConfig(
                minSeverity = if (BuildConfig.DEBUG) Severity.Verbose else Severity.Warn,
                logWriterList = listOf(platformLogWriter()),
            ),
            tag = "Markets",
        ),
    )
}
