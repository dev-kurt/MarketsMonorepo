package com.devkurt.markets.convention

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project

fun LibraryExtension.buildConfigFieldFromLocalProperty(
    project: Project,
    fieldName: String,
    propertyKey: String,
) {
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        buildConfigField("String", fieldName, "\"${project.localProperty(propertyKey)}\"")
    }
}
