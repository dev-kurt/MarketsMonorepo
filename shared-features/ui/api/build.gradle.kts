plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
}

android {
    namespace = "com.devkurt.markets.ui.api"
}

dependencies {
    implementation(libs.coil.compose)
}
