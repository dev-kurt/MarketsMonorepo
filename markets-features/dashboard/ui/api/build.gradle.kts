plugins {
    alias(libs.plugins.marketsAndroidLibrary)
}

android {
    namespace = "com.devkurt.markets.dashboard.ui.api"
}

dependencies {
    api(projects.marketsFeatures.graphDashboard.ui.api)
    api(projects.sharedFeatures.navigation.api)
    api(libs.kotlinx.serialization.core)
}
