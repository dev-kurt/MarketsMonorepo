package com.devkurt.markets.watchlist.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.devkurt.markets.navigation.api.GraphMain
import com.devkurt.markets.navigation.api.GraphMainRoutes
import com.devkurt.markets.serialization.api.MkSerializersModule
import com.devkurt.markets.ui.api.navigation.MkBottomSheetSceneStrategy
import com.devkurt.markets.watchlist.data.local.WatchlistData
import com.devkurt.markets.watchlist.data.local.WatchlistSerializer
import com.devkurt.markets.watchlist.data.remote.api.WatchlistRemoteApi
import com.devkurt.markets.watchlist.data.repository.WatchlistRepositoryImpl
import com.devkurt.markets.watchlist.domain.api.repository.WatchlistRepository
import com.devkurt.markets.watchlist.domain.api.usecase.FlowWatchlistIdsUseCase
import com.devkurt.markets.watchlist.domain.api.usecase.GetWatchlistCoinsUseCase
import com.devkurt.markets.watchlist.domain.api.usecase.ToggleWatchlistUseCase
import com.devkurt.markets.watchlist.domain.impl.usecase.FlowWatchlistIdsUseCaseImpl
import com.devkurt.markets.watchlist.domain.impl.usecase.GetWatchlistCoinsUseCaseImpl
import com.devkurt.markets.watchlist.domain.impl.usecase.ToggleWatchlistUseCaseImpl
import com.devkurt.markets.watchlist.ui.api.WatchlistRoute
import com.devkurt.markets.watchlist.ui.impl.WatchlistViewModel
import com.devkurt.markets.watchlist.ui.impl.WatchlistWrapper
import io.ktor.client.HttpClient
import kotlinx.serialization.modules.polymorphic
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Factory
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

private const val WATCHLIST_FILE = "watchlist.json"

@Module
@Configuration
class WatchlistModule {
    @Single
    @Named("watchlistRoutes")
    fun watchlistRoutes(): GraphMainRoutes = GraphMainRoutes { scope ->
        scope.entry<WatchlistRoute>(
            metadata = MkBottomSheetSceneStrategy.bottomSheet(),
        ) {
            WatchlistWrapper()
        }
    }

    @Single
    @Named("watchlistRouteSerializers")
    fun watchlistRouteSerializers(): MkSerializersModule = MkSerializersModule {
        polymorphic(GraphMain::class) {
            subclass(WatchlistRoute::class, WatchlistRoute.serializer())
        }
    }

    @KoinViewModel
    fun watchlistViewModel(
        flowWatchlistIdsUseCase: FlowWatchlistIdsUseCase,
        getWatchlistCoinsUseCase: GetWatchlistCoinsUseCase,
        toggleWatchlistUseCase: ToggleWatchlistUseCase,
    ): WatchlistViewModel = WatchlistViewModel(
        flowWatchlistIdsUseCase = flowWatchlistIdsUseCase,
        getWatchlistCoinsUseCase = getWatchlistCoinsUseCase,
        toggleWatchlistUseCase = toggleWatchlistUseCase,
    )

    @Single
    fun watchlistDataStore(context: Context): DataStore<WatchlistData> =
        DataStoreFactory.create(
            serializer = WatchlistSerializer,
            produceFile = { context.dataStoreFile(WATCHLIST_FILE) },
        )

    @Single
    fun watchlistRemoteApi(httpClient: HttpClient): WatchlistRemoteApi =
        WatchlistRemoteApi(httpClient = httpClient)

    @Single
    fun watchlistRepository(
        dataStore: DataStore<WatchlistData>,
        remoteApi: WatchlistRemoteApi,
    ): WatchlistRepository = WatchlistRepositoryImpl(
        dataStore = dataStore,
        watchlistRemoteApi = remoteApi,
    )

    @Factory
    fun flowWatchlistIdsUseCase(repository: WatchlistRepository): FlowWatchlistIdsUseCase =
        FlowWatchlistIdsUseCaseImpl(repository = repository)

    @Factory
    fun toggleWatchlistUseCase(repository: WatchlistRepository): ToggleWatchlistUseCase =
        ToggleWatchlistUseCaseImpl(repository = repository)

    @Factory
    fun getWatchlistCoinsUseCase(repository: WatchlistRepository): GetWatchlistCoinsUseCase =
        GetWatchlistCoinsUseCaseImpl(repository = repository)
}
