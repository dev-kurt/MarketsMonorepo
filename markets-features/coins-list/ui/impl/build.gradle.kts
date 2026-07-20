plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
}

android {
    namespace = "com.devkurt.markets.coins_list.ui.impl"
}

dependencies {
    api(projects.marketsFeatures.coinsList.domain.api)
    api(projects.marketsFeatures.watchlist.domain.api)
    api(projects.sharedFeatures.ui.api)
    implementation(projects.sharedFeatures.paging.api)
    implementation(projects.sharedFeatures.navigation.api)
    implementation(projects.marketsFeatures.coinDetail.ui.api)
    implementation(libs.koin.androidx.compose)
    implementation(libs.androidx.compose.material.icons.core)
}
