plugins {
    alias(libs.plugins.marketsAndroidFeatureUi)
}

android {
    namespace = "com.devkurt.markets.graph_list.ui.impl"
}

dependencies {
    api(projects.marketsFeatures.graphList.ui.api)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.koin.androidx.compose)
}
