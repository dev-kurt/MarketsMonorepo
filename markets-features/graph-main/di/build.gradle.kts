plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
    alias(libs.plugins.marketsKoin)
}

android {
    namespace = "com.devkurt.markets.graph_main.di"
}

dependencies {
    api(projects.marketsFeatures.graphMain.ui.impl)
    api(projects.sharedFeatures.navigation.api)
    implementation(libs.koin.android)
    implementation(libs.koin.core.viewmodel)
    implementation(libs.coil.core)
}
