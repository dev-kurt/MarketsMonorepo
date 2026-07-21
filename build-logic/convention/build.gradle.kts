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
    implementation(libs.detekt.gradlePlugin)
    implementation(libs.dependency.analysis.gradlePlugin)
    implementation(libs.kotlin.metadata.jvm)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "markets.android.application"
            implementationClass =
                "com.devkurt.markets.convention.role.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "markets.android.library"
            implementationClass =
                "com.devkurt.markets.convention.role.AndroidLibraryConventionPlugin"
        }
        register("jvmLibrary") {
            id = "markets.jvm.library"
            implementationClass = "com.devkurt.markets.convention.role.JvmLibraryConventionPlugin"
        }
        register("androidFeatureUi") {
            id = "markets.android.feature.ui"
            implementationClass =
                "com.devkurt.markets.convention.role.AndroidFeatureUiConventionPlugin"
        }
        register("koin") {
            id = "markets.koin"
            implementationClass = "com.devkurt.markets.convention.primitive.KoinConventionPlugin"
        }
        register("detekt") {
            id = "markets.detekt"
            implementationClass = "com.devkurt.markets.convention.primitive.DetektConventionPlugin"
        }
        register("dependencyAnalysis") {
            id = "markets.dependency.analysis"
            implementationClass =
                "com.devkurt.markets.convention.primitive.DependencyAnalysisConventionPlugin"
        }
        register("createLayer") {
            id = "markets.create.layer"
            implementationClass = "com.devkurt.markets.convention.primitive.CreateLayerPlugin"
        }
    }
}
