plugins {
    alias(libs.plugins.marketsJvmLibrary)
}

dependencies {
    api(projects.marketsFeatures.watchlist.domain.api)
}
