plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
}

android {
    namespace = "com.devkurt.markets.watchlist.ui.impl"
}

dependencies {
    api(projects.marketsFeatures.watchlist.domain.api)
    implementation(projects.marketsFeatures.coinDetail.ui.api)
    implementation(projects.sharedFeatures.navigation.api)
    api(projects.sharedFeatures.ui.api)
    implementation(libs.koin.androidx.compose)
    implementation(libs.androidx.compose.material.icons.core)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}
