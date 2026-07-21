plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
    alias(libs.plugins.marketsKoin)
}

android {
    namespace = "com.devkurt.markets.search.di"
}

dependencies {
    api(projects.marketsFeatures.search.data)
    api(projects.marketsFeatures.search.domain.api)
    api(projects.marketsFeatures.search.ui.api)
    api(projects.marketsFeatures.search.ui.impl)
    api(projects.sharedFeatures.navigation.api)
    api(projects.sharedFeatures.serialization.api)
    api(libs.ktor.client.core)
    implementation(projects.sharedFeatures.ui.api)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.koin.core.viewmodel)
    implementation(libs.kotlinx.serialization.core)
}
