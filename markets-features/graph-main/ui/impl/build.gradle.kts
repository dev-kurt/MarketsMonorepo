plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
}

android {
    namespace = "com.devkurt.markets.graph_main.ui.impl"
}

dependencies {
    api(projects.sharedFeatures.ui.api)
    api(projects.sharedFeatures.navigation.api)
    implementation(projects.marketsFeatures.graphBottom.ui.api)
    implementation(projects.marketsFeatures.graphDashboard.ui.api)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.koin.androidx.compose)
    implementation(libs.kotlinx.serialization.core)
}
