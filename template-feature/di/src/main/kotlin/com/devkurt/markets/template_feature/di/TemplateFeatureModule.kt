package com.devkurt.markets.template_feature.di

import com.devkurt.markets.navigation.api.GraphEntryProvider
import com.devkurt.markets.navigation.api.GraphMainRoutes
import com.devkurt.markets.template_feature.data.remote.api.TemplateFeatureRemoteApi
import com.devkurt.markets.template_feature.data.repository.TemplateFeatureRepositoryImpl
import com.devkurt.markets.template_feature.domain.api.repository.TemplateFeatureRepository
import com.devkurt.markets.template_feature.domain.api.usecase.TemplateFeatureUseCase
import com.devkurt.markets.template_feature.domain.impl.usecase.TemplateFeatureUseCaseImpl
import com.devkurt.markets.template_feature.ui.api.TemplateFeatureRoute
import com.devkurt.markets.template_feature.ui.impl.TemplateFeatureViewModel
import com.devkurt.markets.template_feature.ui.impl.TemplateFeatureWrapper
import io.ktor.client.HttpClient
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Factory
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@Configuration
class TemplateFeatureModule {
    @Single(binds = [GraphEntryProvider::class])
    @Named("templateFeatureRoutes")
    fun templateFeatureRoutes(): GraphMainRoutes = GraphMainRoutes { scope ->
        scope.entry<TemplateFeatureRoute> {
            TemplateFeatureWrapper()
        }
    }

    @KoinViewModel
    fun templateFeatureViewModel(
        templateFeatureUseCase: TemplateFeatureUseCase,
    ): TemplateFeatureViewModel = TemplateFeatureViewModel(
        templateFeatureUseCase = templateFeatureUseCase,
    )

    @Single
    fun templateFeatureRemoteApi(httpClient: HttpClient): TemplateFeatureRemoteApi =
        TemplateFeatureRemoteApi(httpClient = httpClient)

    @Single
    fun templateFeatureRepository(remoteApi: TemplateFeatureRemoteApi): TemplateFeatureRepository =
        TemplateFeatureRepositoryImpl(templateFeatureRemoteApi = remoteApi)

    @Factory
    fun templateFeatureUseCase(repository: TemplateFeatureRepository): TemplateFeatureUseCase =
        TemplateFeatureUseCaseImpl(repository = repository)
}
