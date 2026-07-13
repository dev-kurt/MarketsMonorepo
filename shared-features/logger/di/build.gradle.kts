plugins {
    alias(libs.plugins.marketsAndroidLibrary)
    alias(libs.plugins.marketsKoin)
}

android {
    namespace = "com.devkurt.markets.logger.di"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.sharedFeatures.logger.impl)
    implementation(libs.kermit)
}
