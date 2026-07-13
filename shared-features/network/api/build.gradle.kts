plugins {
    alias(libs.plugins.marketsAndroidLibrary)
}

android {
    namespace = "com.devkurt.markets.network.api"
}

dependencies {
    api(libs.ktor.client.core)
}
