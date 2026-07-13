plugins {
    alias(libs.plugins.marketsAndroidLibrary)
}

android {
    namespace = "com.devkurt.markets.logger.impl"
}

dependencies {
    api(projects.sharedFeatures.logger.api)
    implementation(libs.kermit)
}
