plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
    alias(libs.plugins.marketsKoin)
}

android {
    namespace = "com.devkurt.markets.template_feature.di"
}

dependencies {
    api(projects.templateFeature.data)
    api(projects.templateFeature.domain.api)
    implementation(projects.templateFeature.domain.impl)
    api(projects.templateFeature.ui.api)
    api(projects.templateFeature.ui.impl)
    api(projects.sharedFeatures.navigation.api)
    api(libs.ktor.client.core)
    implementation(libs.koin.core.viewmodel)
}
