plugins {
    alias(libs.plugins.marketsAndroidLibrary)
}

android {
    namespace = "com.devkurt.markets.network.impl"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(projects.sharedFeatures.network.api)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.kotlinx.serialization.json)
}
