plugins {
    alias(libs.plugins.marketsJvmLibrary)
    alias(libs.plugins.kotlinSerialization)
}

dependencies {
    api(libs.kotlinx.serialization.core)
    api(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test.junit)
}
