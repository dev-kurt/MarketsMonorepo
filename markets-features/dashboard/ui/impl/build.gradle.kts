plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
}

android {
    namespace = "com.devkurt.markets.dashboard.ui.impl"
}

dependencies {
    api(projects.marketsFeatures.watchlist.domain.api)
    api(projects.sharedFeatures.ui.api)
    implementation(projects.sharedFeatures.navigation.api)
    implementation(projects.marketsFeatures.watchlist.ui.api)
    implementation(projects.marketsFeatures.watchlist.ui.impl)
    implementation(projects.marketsFeatures.coinDetail.ui.api)
    implementation(libs.koin.androidx.compose)
}
