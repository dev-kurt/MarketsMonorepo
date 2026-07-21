package com.devkurt.markets.convention.role

import com.devkurt.markets.convention.JVM_TOOLCHAIN
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.jvm")
        pluginManager.apply("markets.detekt")

        extensions.getByType<KotlinJvmProjectExtension>().jvmToolchain(JVM_TOOLCHAIN)
    }
}
