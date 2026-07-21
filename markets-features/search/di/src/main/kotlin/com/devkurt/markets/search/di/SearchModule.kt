package com.devkurt.markets.search.di

import androidx.compose.animation.togetherWith
import androidx.navigation3.ui.NavDisplay
import com.devkurt.markets.navigation.api.GraphMain
import com.devkurt.markets.navigation.api.GraphMainRoutes
import com.devkurt.markets.search.data.remote.api.SearchRemoteApi
import com.devkurt.markets.search.data.repository.SearchRepositoryImpl
import com.devkurt.markets.search.domain.api.repository.SearchRepository
import com.devkurt.markets.search.ui.api.SearchRoute
import com.devkurt.markets.search.ui.impl.SearchViewModel
import com.devkurt.markets.search.ui.impl.SearchWrapper
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
class SearchModule {
    @Single
    @Named("searchRoutes")
    fun searchRoutes(): GraphMainRoutes = GraphMainRoutes { scope ->
        scope.entry<SearchRoute>(
            metadata = NavDisplay.transitionSpec {
                MkEnterTransition.slideInBottom togetherWith MkExitTransition.fadeOut
            } + NavDisplay.popTransitionSpec {
                MkEnterTransition.fadeIn togetherWith MkExitTransition.slideOutBottom
            } + NavDisplay.predictivePopTransitionSpec {
                MkEnterTransition.fadeIn togetherWith MkExitTransition.slideOutBottom
            },
        ) {
            SearchWrapper()
        }
    }

    @Single
    @Named("searchRouteSerializers")
    fun searchRouteSerializers(): MkSerializersModule = MkSerializersModule {
        polymorphic(GraphMain::class) {
            subclass(SearchRoute::class, SearchRoute.serializer())
        }
    }

    @KoinViewModel
    fun searchViewModel(
        searchRepository: SearchRepository,
    ): SearchViewModel = SearchViewModel(
        searchRepository = searchRepository,
    )

    @Single
    fun searchRemoteApi(httpClient: HttpClient): SearchRemoteApi =
        SearchRemoteApi(httpClient = httpClient)

    @Single
    fun searchRepository(remoteApi: SearchRemoteApi): SearchRepository =
        SearchRepositoryImpl(searchRemoteApi = remoteApi)
}
