plugins {
    alias(libs.plugins.marketsAndroidApplication)
}

android {
    namespace = "com.devkurt.markets"

    defaultConfig {
        applicationId = "com.devkurt.markets"
    }
}

dependencies {
    implementation(projects.marketsFeatures.graphMain.di)
}
