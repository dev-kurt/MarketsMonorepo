plugins {
    alias(libs.plugins.marketsJvmLibrary)
}

dependencies {
    api(libs.androidx.compose.runtime.annotation)
    api(libs.kotlinx.coroutines.core)
}
