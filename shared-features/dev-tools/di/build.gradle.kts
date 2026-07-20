plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
    alias(libs.plugins.marketsKoin)
}

android {
    namespace = "com.devkurt.markets.dev_tools.di"
}

dependencies {
    api(projects.sharedFeatures.devTools.ui.api)
    api(projects.sharedFeatures.devTools.ui.impl)
    api(projects.sharedFeatures.navigation.api)
    api(projects.sharedFeatures.serialization.api)
    implementation(projects.sharedFeatures.ui.api)
    implementation(libs.koin.core.viewmodel)
    implementation(libs.kotlinx.serialization.core)
}
