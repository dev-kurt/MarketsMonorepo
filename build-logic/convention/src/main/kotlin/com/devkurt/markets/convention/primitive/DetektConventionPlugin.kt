package com.devkurt.markets.convention.primitive

import com.devkurt.markets.convention.JVM_TOOLCHAIN
import dev.detekt.gradle.Detekt
import dev.detekt.gradle.DetektCreateBaselineTask
import dev.detekt.gradle.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("dev.detekt")

        configure<DetektExtension> {
            buildUponDefaultConfig.set(true)
            allRules.set(true)
            autoCorrect.set(false)
            parallel.set(true)
            config.setFrom("${rootProject.projectDir}/config/detekt/detekt.yml")
            source.setFrom(
                "src/main/kotlin",
                "src/main/java",
            )
        }

        tasks.withType<Detekt>().configureEach {
            jvmTarget.set(JVM_TOOLCHAIN.toString())
            exclude { spec -> spec.file.invariantSeparatorsPath.contains("/build/") }
            reports {
                html.required.set(true)
                sarif.required.set(true)
            }
        }

        tasks.withType<DetektCreateBaselineTask>().configureEach {
            jvmTarget.set(JVM_TOOLCHAIN.toString())
        }
    }
}
