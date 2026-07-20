package com.devkurt.markets.coin_detail.di

import com.devkurt.markets.coin_detail.data.remote.api.CoinDetailRemoteApi
import com.devkurt.markets.coin_detail.data.repository.CoinDetailRepositoryImpl
import com.devkurt.markets.coin_detail.domain.api.repository.CoinDetailRepository
import com.devkurt.markets.coin_detail.domain.api.usecase.CoinDetailUseCase
import com.devkurt.markets.coin_detail.domain.impl.usecase.CoinDetailUseCaseImpl
import com.devkurt.markets.coin_detail.ui.api.CoinDetailRoute
import com.devkurt.markets.coin_detail.ui.impl.CoinDetailViewModel
import com.devkurt.markets.coin_detail.ui.impl.CoinDetailWrapper
import com.devkurt.markets.graph_list.ui.api.GraphList
import com.devkurt.markets.graph_list.ui.api.GraphListRoutes
import com.devkurt.markets.graph_list.ui.api.LocalGraphList
import com.devkurt.markets.navigation.api.GraphMain
import com.devkurt.markets.navigation.api.GraphMainRoutes
import com.devkurt.markets.navigation.api.LocalGraphMain
import com.devkurt.markets.navigation.api.safePop
import com.devkurt.markets.serialization.api.MkSerializersModule
import io.ktor.client.HttpClient
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@Configuration
class CoinDetailModule {
    @Single
    @Named("coinDetailListRoutes")
    fun coinDetailListRoutes(): GraphListRoutes = GraphListRoutes { scope ->
        scope.entry<CoinDetailRoute> { route ->
            val listGraph = LocalGraphList.current
            CoinDetailWrapper(
                coinId = route.coinId,
                onBack = { listGraph.safePop() },
            )
        }
    }

    @Single
    @Named("coinDetailMainRoutes")
    fun coinDetailMainRoutes(): GraphMainRoutes = GraphMainRoutes { scope ->
        scope.entry<CoinDetailRoute> { route ->
            val mainGraph = LocalGraphMain.current
            CoinDetailWrapper(
                coinId = route.coinId,
                onBack = { mainGraph.safePop() },
            )
        }
    }

    @Single
    @Named("coinDetailRouteSerializers")
    fun coinDetailRouteSerializers(): MkSerializersModule = MkSerializersModule {
        polymorphic(GraphList::class) {
            subclass(CoinDetailRoute::class, CoinDetailRoute.serializer())
        }
        polymorphic(GraphMain::class) {
            subclass(CoinDetailRoute::class, CoinDetailRoute.serializer())
        }
    }

    @KoinViewModel
    fun coinDetailViewModel(
        @InjectedParam coinId: String,
        coinDetailUseCase: CoinDetailUseCase,
    ): CoinDetailViewModel = CoinDetailViewModel(
        coinId = coinId,
        coinDetailUseCase = coinDetailUseCase,
    )

    @Single
    fun coinDetailRemoteApi(httpClient: HttpClient): CoinDetailRemoteApi =
        CoinDetailRemoteApi(httpClient = httpClient)

    @Single
    fun coinDetailRepository(remoteApi: CoinDetailRemoteApi): CoinDetailRepository =
        CoinDetailRepositoryImpl(coinDetailRemoteApi = remoteApi)

    @Factory
    fun coinDetailUseCase(repository: CoinDetailRepository): CoinDetailUseCase =
        CoinDetailUseCaseImpl(repository = repository)
}
