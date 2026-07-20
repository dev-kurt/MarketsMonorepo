import com.devkurt.markets.convention.buildConfigFieldFromLocalProperty

plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
    alias(libs.plugins.marketsKoin)
}

android {
    namespace = "com.devkurt.markets.graph_main.di"

    buildConfigFieldFromLocalProperty(project, "CG_DEMO_API_KEY", "cgDemoApiKey")
}

dependencies {
    api(projects.marketsFeatures.graphMain.ui.impl)
    api(projects.sharedFeatures.navigation.api)

    implementation(projects.sharedFeatures.ui.di)
    implementation(projects.sharedFeatures.logger.di)
    implementation(projects.sharedFeatures.network.di)
    api(projects.sharedFeatures.network.api)
    implementation(projects.sharedFeatures.coroutines.di)
    implementation(projects.sharedFeatures.navigation.di)
    implementation(projects.sharedFeatures.serialization.di)
    implementation(projects.marketsFeatures.graphBottom.di)
    implementation(projects.marketsFeatures.graphDashboard.di)
    implementation(projects.marketsFeatures.graphList.di)
    implementation(projects.marketsFeatures.coinsList.di)
    implementation(projects.marketsFeatures.coinDetail.di)
    implementation(projects.marketsFeatures.watchlist.di)
    implementation(projects.marketsFeatures.dashboard.di)

    implementation(libs.koin.android)
    implementation(libs.koin.core.viewmodel)
    implementation(libs.coil.core)
    implementation(libs.androidx.activity.compose)

    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.junit)
}
