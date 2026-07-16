plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
}

android {
    namespace = "com.devkurt.markets.template_feature.ui.impl"
}

dependencies {
    api(projects.templateFeature.domain.api)
    api(projects.sharedFeatures.ui.api)
    implementation(projects.sharedFeatures.navigation.api)
    implementation(libs.koin.androidx.compose)
    implementation(libs.androidx.compose.material.icons.core)
}
