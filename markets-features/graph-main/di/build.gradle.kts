plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
    alias(libs.plugins.marketsKoin)
}

android {
    namespace = "com.devkurt.markets.graph_main.di"
}

dependencies {
    api(projects.marketsFeatures.graphMain.ui.impl)
    api(projects.sharedFeatures.navigation.api)

    implementation(projects.sharedFeatures.ui.di)
    implementation(projects.sharedFeatures.logger.di)
    implementation(projects.sharedFeatures.network.di)
    implementation(projects.sharedFeatures.coroutines.di)
    implementation(projects.sharedFeatures.navigation.di)
    implementation(projects.sharedFeatures.serialization.di)
    implementation(projects.marketsFeatures.graphBottom.di)
    implementation(projects.marketsFeatures.graphDashboard.di)

    implementation(libs.koin.android)
    implementation(libs.koin.core.viewmodel)
    implementation(libs.coil.core)
    implementation(libs.androidx.activity.compose)

    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.junit)
}
