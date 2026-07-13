plugins {
    alias(libs.plugins.marketsAndroidLibrary)
    alias(libs.plugins.marketsKoin)
}

android {
    namespace = "com.devkurt.markets.coroutines.di"
}

dependencies {
    api(libs.kotlinx.coroutines.core)
    implementation(projects.sharedFeatures.logger.api)
}
