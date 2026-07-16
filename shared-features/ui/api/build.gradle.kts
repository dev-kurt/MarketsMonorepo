plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
}

android {
    namespace = "com.devkurt.markets.ui.api"
}

dependencies {
    implementation(libs.coil.compose)

    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
