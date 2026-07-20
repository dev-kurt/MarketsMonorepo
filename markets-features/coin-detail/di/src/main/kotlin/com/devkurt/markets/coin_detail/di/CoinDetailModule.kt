package com.devkurt.markets.coin_detail.di

import androidx.compose.animation.togetherWith
import androidx.navigation3.ui.NavDisplay
import com.devkurt.markets.coin_detail.data.remote.api.CoinDetailRemoteApi
import com.devkurt.markets.coin_detail.data.repository.CoinDetailRepositoryImpl
import com.devkurt.markets.coin_detail.domain.api.repository.CoinDetailRepository
import com.devkurt.markets.coin_detail.ui.api.CoinDetailRoute
import com.devkurt.markets.coin_detail.ui.impl.CoinDetailViewModel
import com.devkurt.markets.coin_detail.ui.impl.CoinDetailWrapper
import com.devkurt.markets.navigation.api.GraphMain
import com.devkurt.markets.navigation.api.GraphMainRoutes
import com.devkurt.markets.serialization.api.MkSerializersModule
import com.devkurt.markets.ui.api.motion.MkEnterTransition
import com.devkurt.markets.ui.api.motion.MkExitTransition
import io.ktor.client.HttpClient
import kotlinx.serialization.modules.polymorphic
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@Configuration
class CoinDetailModule {
    @Single
    @Named("coinDetailRoutes")
    fun coinDetailRoutes(): GraphMainRoutes = GraphMainRoutes { scope ->
        scope.entry<CoinDetailRoute>(
            metadata = NavDisplay.transitionSpec {
                MkEnterTransition.slideInRight togetherWith MkExitTransition.slideOutLeft
            } + NavDisplay.popTransitionSpec {
                MkEnterTransition.slideInLeft togetherWith MkExitTransition.slideOutRight
            } + NavDisplay.predictivePopTransitionSpec {
                MkEnterTransition.slideInLeft togetherWith MkExitTransition.slideOutRight
            },
        ) { route ->
            CoinDetailWrapper(route = route)
        }
    }

    @Single
    @Named("coinDetailRouteSerializers")
    fun coinDetailRouteSerializers(): MkSerializersModule = MkSerializersModule {
        polymorphic(GraphMain::class) {
            subclass(CoinDetailRoute::class, CoinDetailRoute.serializer())
        }
    }

    @KoinViewModel
    fun coinDetailViewModel(
        route: CoinDetailRoute,
        coinDetailRepository: CoinDetailRepository,
    ): CoinDetailViewModel = CoinDetailViewModel(
        route = route,
        coinDetailRepository = coinDetailRepository,
    )

    @Single
    fun coinDetailRemoteApi(httpClient: HttpClient): CoinDetailRemoteApi =
        CoinDetailRemoteApi(httpClient = httpClient)

    @Single
    fun coinDetailRepository(remoteApi: CoinDetailRemoteApi): CoinDetailRepository =
        CoinDetailRepositoryImpl(coinDetailRemoteApi = remoteApi)
}
