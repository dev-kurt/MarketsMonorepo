plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
    alias(libs.plugins.marketsKoin)
}

android {
    namespace = "com.devkurt.markets.coins_list.di"
}

dependencies {
    api(projects.marketsFeatures.coinsList.data)
    api(projects.marketsFeatures.coinsList.domain.api)
    api(projects.marketsFeatures.watchlist.domain.api)
    api(projects.marketsFeatures.coinsList.ui.api)
    api(projects.marketsFeatures.coinsList.ui.impl)
    api(projects.marketsFeatures.graphList.ui.api)
    api(projects.sharedFeatures.navigation.api)
    api(projects.sharedFeatures.serialization.api)
    api(libs.ktor.client.core)
    implementation(libs.koin.core.viewmodel)
    implementation(libs.kotlinx.serialization.core)
}
