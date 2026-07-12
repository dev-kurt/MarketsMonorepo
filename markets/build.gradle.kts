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
}
