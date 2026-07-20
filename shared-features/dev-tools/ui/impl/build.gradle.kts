plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
}

android {
    namespace = "com.devkurt.markets.dev_tools.ui.impl"
}

dependencies {
    api(projects.sharedFeatures.devTools.ui.api)
    api(projects.sharedFeatures.ui.api)
    implementation(projects.sharedFeatures.navigation.api)
    implementation(libs.koin.androidx.compose)
}
