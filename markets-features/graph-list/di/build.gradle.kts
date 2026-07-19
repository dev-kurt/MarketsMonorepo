plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
    alias(libs.plugins.marketsKoin)
}

android {
    namespace = "com.devkurt.markets.graph_list.di"
}

dependencies {
    api(projects.marketsFeatures.graphList.ui.api)
    api(projects.marketsFeatures.graphList.ui.impl)
    api(projects.marketsFeatures.graphBottom.ui.api)
    api(projects.sharedFeatures.navigation.api)
    api(projects.sharedFeatures.serialization.api)
    implementation(libs.koin.core.viewmodel)
    implementation(libs.kotlinx.serialization.core)
}
