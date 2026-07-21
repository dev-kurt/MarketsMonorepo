plugins {
    alias(libs.plugins.marketsAndroidLibrary)
    alias(libs.plugins.marketsKoin)
}

android {
    namespace = "com.devkurt.markets.network.di"
}

dependencies {
    api(projects.sharedFeatures.serialization.api)
    implementation(projects.sharedFeatures.network.impl)
    api(projects.sharedFeatures.network.api)
    implementation(libs.ktor.client.core)
    implementation(libs.kotlinx.serialization.json)
}
