package com.devkurt.markets.convention.role

import com.android.build.api.dsl.ApplicationExtension
import com.devkurt.markets.convention.COMPILE_SDK
import com.devkurt.markets.convention.JVM_TOOLCHAIN
import com.devkurt.markets.convention.MIN_SDK
import com.devkurt.markets.convention.TARGET_SDK
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.android.application")

        extensions.configure<ApplicationExtension> {
            compileSdk = COMPILE_SDK
            defaultConfig {
                minSdk = MIN_SDK
                targetSdk = TARGET_SDK
                versionCode = 1
                versionName = "1.0"
            }
        }

        extensions.getByType<KotlinAndroidProjectExtension>().jvmToolchain(JVM_TOOLCHAIN)
    }
}
