plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
}

android {
    namespace = "com.devkurt.markets.graph_dashboard.ui.impl"
}

dependencies {
    api(projects.marketsFeatures.graphDashboard.ui.api)
    api(projects.sharedFeatures.ui.api)
    implementation(projects.sharedFeatures.navigation.api)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.koin.androidx.compose)
}
