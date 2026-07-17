plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
}

android {
    namespace = "com.devkurt.markets.graph_bottom.ui.api"
}

dependencies {
    api(projects.sharedFeatures.navigation.api)
    api(libs.kotlinx.serialization.core)
}
