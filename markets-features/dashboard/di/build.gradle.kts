plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
    alias(libs.plugins.marketsKoin)
}

android {
    namespace = "com.devkurt.markets.dashboard.di"
}

dependencies {
    api(projects.marketsFeatures.dashboard.ui.api)
    api(projects.marketsFeatures.dashboard.ui.impl)
    api(projects.marketsFeatures.watchlist.domain.api)
    api(projects.marketsFeatures.graphDashboard.ui.api)
    api(projects.sharedFeatures.navigation.api)
    api(projects.sharedFeatures.serialization.api)
    implementation(libs.koin.core.viewmodel)
    implementation(libs.kotlinx.serialization.core)
}
