plugins {
    alias(libs.plugins.marketsAndroidLibrary)
}

android {
    namespace = "com.devkurt.markets.search.data"
}

dependencies {
    api(projects.marketsFeatures.search.domain.api)
    api(projects.sharedFeatures.network.api)
    implementation(projects.sharedFeatures.serialization.api)
    api(libs.ktor.client.core)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.ktor.client.content.negotiation)
    testImplementation(libs.ktor.serialization.kotlinx.json)
}
