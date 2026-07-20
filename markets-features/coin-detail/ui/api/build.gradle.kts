plugins {
    alias(libs.plugins.marketsAndroidLibrary)
}

android {
    namespace = "com.devkurt.markets.coin_detail.ui.api"
}

dependencies {
    api(projects.marketsFeatures.graphList.ui.api)
    api(projects.sharedFeatures.navigation.api)
    api(libs.kotlinx.serialization.core)
}
