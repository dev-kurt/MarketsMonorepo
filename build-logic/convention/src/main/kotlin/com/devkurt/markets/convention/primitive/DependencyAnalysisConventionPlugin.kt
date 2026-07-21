package com.devkurt.markets.convention.primitive

import com.autonomousapps.DependencyAnalysisExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class DependencyAnalysisConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val enabled = providers.gradleProperty("dagp.enabled")
                .getOrElse("false").toBoolean()
            if (!enabled) return

            pluginManager.apply("com.autonomousapps.dependency-analysis")

            subprojects {
                pluginManager.apply("com.autonomousapps.dependency-analysis")
            }

            extensions.configure<DependencyAnalysisExtension> {
                issues {
                    all {
                        onUnusedDependencies {
                            severity("fail")
                            // Injected into every feature module by marketsAndroidFeatureUi;
                            // di modules legitimately use only a subset of the stack.
                            exclude(
                                "androidx.compose.foundation:foundation",
                                "androidx.compose.material3:material3",
                                "androidx.compose.ui:ui",
                                "androidx.compose.ui:ui-tooling-preview",
                                "androidx.lifecycle:lifecycle-runtime-compose",
                                "androidx.lifecycle:lifecycle-viewmodel-compose",
                            )
                        }
                    }
                }

                structure {
                    bundle("androidx-paging") {
                        primary("androidx.paging:paging-common")
                        includeGroup("androidx.paging")
                    }
                    bundle("androidx-datastore") {
                        primary("androidx.datastore:datastore")
                        includeGroup("androidx.datastore")
                    }
                }
            }
        }
    }
}
