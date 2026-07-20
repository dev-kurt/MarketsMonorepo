package com.devkurt.markets.coroutines.di

import com.devkurt.markets.logger.api.Logger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@Configuration
class CoroutinesScopesModule {
    @Single
    fun exceptionHandler(logger: Logger): CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            logger.e("IOScope", throwable) { "Uncaught coroutine error" }
        }

    @Single
    @Named("IOScope")
    fun provideIoScope(handler: CoroutineExceptionHandler): CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.IO + handler)
}
