plugins {
    alias(libs.plugins.marketsAndroidLibrary)
}

android {
    namespace = "com.devkurt.markets.search.ui.api"
}

dependencies {
    api(projects.sharedFeatures.navigation.api)
    api(libs.kotlinx.serialization.core)
}
