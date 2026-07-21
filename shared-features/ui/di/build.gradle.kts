plugins {
    alias(libs.plugins.marketsAndroidLibrary)
    alias(libs.plugins.marketsKoin)
}

android {
    namespace = "com.devkurt.markets.ui.di"
}

dependencies {
    api(libs.coil.core)
    implementation(libs.coil.network.ktor3)
    implementation(libs.ktor.client.core)
}
