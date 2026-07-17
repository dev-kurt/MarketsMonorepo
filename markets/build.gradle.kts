plugins {
    alias(libs.plugins.marketsAndroidApplication)
    alias(libs.plugins.jetbrainsKotlinPluginCompose)
}

android {
    namespace = "com.devkurt.markets"

    defaultConfig {
        applicationId = "com.devkurt.markets"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(projects.marketsFeatures.graphMain.ui.impl)
    implementation(projects.marketsFeatures.graphMain.di)
    implementation(projects.marketsFeatures.graphBottom.di)
    implementation(projects.marketsFeatures.graphDashboard.di)
    implementation(projects.sharedFeatures.ui.di)
    implementation(projects.sharedFeatures.navigation.di)
    implementation(projects.sharedFeatures.logger.di)
    implementation(projects.sharedFeatures.network.di)
    implementation(projects.sharedFeatures.coroutines.di)
}
