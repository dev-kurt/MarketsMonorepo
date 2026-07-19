plugins {
    alias(libs.plugins.marketsJvmLibrary)
}

dependencies {
    api(projects.marketsFeatures.coinsList.domain.api)
}
