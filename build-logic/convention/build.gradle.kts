plugins {
    `kotlin-dsl`
}

group = "com.devkurt.markets.buildlogic"

kotlin {
    jvmToolchain(21)
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.koin.compiler.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "markets.android.application"
            implementationClass = "com.devkurt.markets.convention.role.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "markets.android.library"
            implementationClass = "com.devkurt.markets.convention.role.AndroidLibraryConventionPlugin"
        }
        register("jvmLibrary") {
            id = "markets.jvm.library"
            implementationClass = "com.devkurt.markets.convention.role.JvmLibraryConventionPlugin"
        }
        register("androidFeatureUi") {
            id = "markets.android.feature.ui"
            implementationClass = "com.devkurt.markets.convention.role.AndroidFeatureUiConventionPlugin"
        }
        register("koin") {
            id = "markets.koin"
            implementationClass = "com.devkurt.markets.convention.primitive.KoinConventionPlugin"
        }
    }
}
