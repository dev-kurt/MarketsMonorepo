package com.devkurt.markets.coins_list.di

import com.devkurt.markets.coins_list.data.remote.api.CoinsListRemoteApi
import com.devkurt.markets.coins_list.data.repository.CoinsListRepositoryImpl
import com.devkurt.markets.coins_list.domain.api.repository.CoinsListRepository
import com.devkurt.markets.coins_list.domain.api.usecase.CoinsListUseCase
import com.devkurt.markets.coins_list.domain.impl.usecase.CoinsListUseCaseImpl
import com.devkurt.markets.coins_list.ui.api.CoinsListRoute
import com.devkurt.markets.coins_list.ui.impl.CoinsListViewModel
import com.devkurt.markets.coins_list.ui.impl.CoinsListWrapper
import com.devkurt.markets.graph_list.ui.api.GraphList
import com.devkurt.markets.graph_list.ui.api.GraphListRoutes
import com.devkurt.markets.serialization.api.MkSerializersModule
import com.devkurt.markets.watchlist.domain.api.usecase.FlowWatchlistIdsUseCase
import com.devkurt.markets.watchlist.domain.api.usecase.ToggleWatchlistUseCase
import io.ktor.client.HttpClient
import kotlinx.serialization.modules.polymorphic
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Factory
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@Configuration
class CoinsListModule {
    @Single
    @Named("coinsListRoutes")
    fun coinsListRoutes(): GraphListRoutes = GraphListRoutes { scope ->
        scope.entry<CoinsListRoute> {
            CoinsListWrapper()
        }
    }

    @Single
    @Named("coinsListRouteSerializers")
    fun coinsListRouteSerializers(): MkSerializersModule = MkSerializersModule {
        polymorphic(GraphList::class) {
            subclass(CoinsListRoute::class, CoinsListRoute.serializer())
        }
    }

    @KoinViewModel
    fun coinsListViewModel(
        coinsListUseCase: CoinsListUseCase,
        flowWatchlistIdsUseCase: FlowWatchlistIdsUseCase,
        toggleWatchlistUseCase: ToggleWatchlistUseCase,
    ): CoinsListViewModel = CoinsListViewModel(
        coinsListUseCase = coinsListUseCase,
        flowWatchlistIdsUseCase = flowWatchlistIdsUseCase,
        toggleWatchlistUseCase = toggleWatchlistUseCase,
    )

    @Single
    fun coinsListRemoteApi(httpClient: HttpClient): CoinsListRemoteApi =
        CoinsListRemoteApi(httpClient = httpClient)

    @Single
    fun coinsListRepository(remoteApi: CoinsListRemoteApi): CoinsListRepository =
        CoinsListRepositoryImpl(coinsListRemoteApi = remoteApi)

    @Factory
    fun coinsListUseCase(repository: CoinsListRepository): CoinsListUseCase =
        CoinsListUseCaseImpl(repository = repository)
}
