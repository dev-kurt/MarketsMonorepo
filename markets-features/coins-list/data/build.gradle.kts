plugins {
    alias(libs.plugins.marketsAndroidLibrary)
}

android {
    namespace = "com.devkurt.markets.coins_list.data"
}

dependencies {
    api(projects.marketsFeatures.coinsList.domain.api)
    api(projects.sharedFeatures.network.api)
    api(libs.ktor.client.core)
    implementation(libs.kotlinx.serialization.json)
}
