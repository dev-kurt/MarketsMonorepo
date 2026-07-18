plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
    alias(libs.plugins.marketsKoin)
}

android {
    namespace = "com.devkurt.markets.graph_bottom.di"
}

dependencies {
    api(projects.marketsFeatures.graphBottom.ui.api)
    api(projects.marketsFeatures.graphBottom.ui.impl)
    api(projects.sharedFeatures.navigation.api)
    implementation(libs.koin.core.viewmodel)
    implementation(libs.kotlinx.serialization.core)
}
