plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
    alias(libs.plugins.marketsKoin)
}

android {
    namespace = "com.devkurt.markets.graph_dashboard.di"
}

dependencies {
    implementation(libs.androidx.navigation3.ui)
    api(projects.marketsFeatures.graphDashboard.ui.api)
    api(projects.marketsFeatures.graphDashboard.ui.impl)
    api(projects.marketsFeatures.graphBottom.ui.api)
    api(projects.sharedFeatures.navigation.api)
    api(projects.sharedFeatures.serialization.api)
    implementation(libs.koin.core.viewmodel)
    implementation(libs.kotlinx.serialization.core)
}
