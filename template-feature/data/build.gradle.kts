plugins {
    alias(libs.plugins.marketsAndroidLibrary)
}

android {
    namespace = "com.devkurt.markets.template_feature.data"
}

dependencies {
    api(projects.templateFeature.domain.api)
    api(projects.sharedFeatures.network.api)
    api(libs.ktor.client.core)
}
