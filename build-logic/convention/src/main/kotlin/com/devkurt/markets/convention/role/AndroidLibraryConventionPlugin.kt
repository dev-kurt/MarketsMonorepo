package com.devkurt.markets.convention.role

import com.android.build.api.dsl.LibraryExtension
import com.devkurt.markets.convention.COMPILE_SDK
import com.devkurt.markets.convention.JVM_TOOLCHAIN
import com.devkurt.markets.convention.MIN_SDK
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.plugin.serialization")
        }

        extensions.configure<LibraryExtension> {
            compileSdk = COMPILE_SDK
            defaultConfig {
                minSdk = MIN_SDK
            }
        }

        extensions.getByType<KotlinAndroidProjectExtension>().jvmToolchain(JVM_TOOLCHAIN)
    }
}
