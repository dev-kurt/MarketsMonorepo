plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
    alias(libs.plugins.marketsKoin)
}

android {
    namespace = "com.devkurt.markets.watchlist.di"
}

dependencies {
    api(projects.marketsFeatures.watchlist.data)
    api(projects.marketsFeatures.watchlist.domain.api)
    implementation(projects.marketsFeatures.watchlist.domain.impl)
    api(projects.marketsFeatures.watchlist.ui.api)
    api(projects.marketsFeatures.watchlist.ui.impl)
    api(projects.sharedFeatures.navigation.api)
    api(projects.sharedFeatures.serialization.api)
    implementation(projects.sharedFeatures.ui.api)
    implementation(projects.sharedFeatures.devTools.ui.api)
    api(libs.ktor.client.core)
    implementation(libs.koin.core.viewmodel)
    implementation(libs.kotlinx.serialization.core)
}
