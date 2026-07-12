package com.devkurt.markets.convention.primitive

import com.devkurt.markets.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.koin.compiler.plugin.KoinGradleExtension

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.findPlugin("koinCompiler").get().get().pluginId)

        extensions.configure<KoinGradleExtension> {
            compileSafety.set(false)
        }

        dependencies {
            add("implementation", libs.findLibrary("koin-core").get())
            add("implementation", libs.findLibrary("koin-annotations").get())
        }
    }
}
