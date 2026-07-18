plugins {
    alias(libs.plugins.marketsAndroidLibrary)
    alias(libs.plugins.marketsKoin)
}

android {
    namespace = "com.devkurt.markets.navigation.di"
}

dependencies {
    implementation(projects.sharedFeatures.navigation.api)
}
