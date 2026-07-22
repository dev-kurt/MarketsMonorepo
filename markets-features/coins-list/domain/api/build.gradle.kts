plugins {
    alias(libs.plugins.marketsJvmLibrary)
}

dependencies {
    api(libs.androidx.paging.common)
    api(libs.kotlinx.coroutines.core)
}
