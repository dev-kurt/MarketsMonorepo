plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
}

android {
    namespace = "com.devkurt.markets.ui.api"
}

dependencies {
    api(libs.kotlinx.collections.immutable)
    implementation(libs.coil.compose)
    implementation(libs.androidx.navigation3.ui)

    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
