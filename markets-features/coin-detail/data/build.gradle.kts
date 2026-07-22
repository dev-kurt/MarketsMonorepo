plugins {
    alias(libs.plugins.marketsAndroidLibrary)
}

android {
    namespace = "com.devkurt.markets.coin_detail.data"
}

dependencies {
    api(projects.marketsFeatures.coinDetail.domain.api)
    api(projects.sharedFeatures.network.api)
    api(libs.ktor.client.core)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test.junit)
}
