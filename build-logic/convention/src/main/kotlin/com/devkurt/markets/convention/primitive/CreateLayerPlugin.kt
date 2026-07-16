package com.devkurt.markets.convention.primitive

import com.devkurt.markets.convention.task.CreateLayerTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class CreateLayerPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            tasks.register<CreateLayerTask>("createLayer") {
                group = "markets"
                description = "Creates a missing layer in an existing feature module."
                rootDirectory.set(layout.projectDirectory)
            }
        }
    }
}
