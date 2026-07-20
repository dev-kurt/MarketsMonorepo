plugins {
    alias(libs.plugins.marketsJvmLibrary)
}

dependencies {
    api(projects.marketsFeatures.coinDetail.domain.api)
}
