plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
}

android {
    namespace = "com.devkurt.markets.navigation.api"
}

dependencies {
    api(libs.androidx.navigation3.runtime)
}
