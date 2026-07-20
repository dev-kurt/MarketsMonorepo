plugins {
    alias(libs.plugins.marketsAndroidLibrary)
}

android {
    namespace = "com.devkurt.markets.watchlist.data"
}

dependencies {
    api(projects.marketsFeatures.watchlist.domain.api)
    api(projects.sharedFeatures.network.api)
    api(libs.androidx.datastore)
    api(libs.ktor.client.core)
    implementation(libs.kotlinx.serialization.json)
}
