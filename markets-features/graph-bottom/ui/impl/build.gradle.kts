plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
}

android {
    namespace = "com.devkurt.markets.graph_bottom.ui.impl"
}

dependencies {
    api(projects.marketsFeatures.graphBottom.ui.api)
    api(projects.sharedFeatures.ui.api)
    implementation(projects.sharedFeatures.navigation.api)
    implementation(projects.marketsFeatures.graphDashboard.ui.api)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.koin.androidx.compose)
}
