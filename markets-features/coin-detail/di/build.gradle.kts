plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
    alias(libs.plugins.marketsKoin)
}

android {
    namespace = "com.devkurt.markets.coin_detail.di"
}

dependencies {
    api(projects.marketsFeatures.coinDetail.data)
    api(projects.marketsFeatures.coinDetail.domain.api)
    api(projects.marketsFeatures.coinDetail.ui.api)
    api(projects.marketsFeatures.coinDetail.ui.impl)
    api(projects.sharedFeatures.navigation.api)
    api(projects.sharedFeatures.serialization.api)
    api(libs.ktor.client.core)
    implementation(libs.koin.core.viewmodel)
    implementation(libs.kotlinx.serialization.core)
}
