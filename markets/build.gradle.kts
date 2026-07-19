import java.util.Properties

plugins {
    alias(libs.plugins.marketsAndroidApplication)
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) file.inputStream().use { load(it) }
}
val cgDemoApiKey: String = localProperties.getProperty("cgDemoApiKey").orEmpty()

android {
    namespace = "com.devkurt.markets"

    defaultConfig {
        applicationId = "com.devkurt.markets"
        buildConfigField("String", "CG_DEMO_API_KEY", "\"$cgDemoApiKey\"")
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.marketsFeatures.graphMain.di)
}
