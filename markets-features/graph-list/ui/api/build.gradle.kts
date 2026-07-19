plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
}

android {
    namespace = "com.devkurt.markets.graph_list.ui.api"
}

dependencies {
    api(projects.sharedFeatures.navigation.api)
    api(projects.marketsFeatures.graphBottom.ui.api)
    api(libs.kotlinx.serialization.core)
}
