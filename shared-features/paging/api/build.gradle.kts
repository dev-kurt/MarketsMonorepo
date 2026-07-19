plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
}

android {
    namespace = "com.devkurt.markets.paging.api"
}

dependencies {
    api(libs.androidx.paging.common)
    api(libs.androidx.paging.compose)
    api(libs.kotlinx.coroutines.core)
}
