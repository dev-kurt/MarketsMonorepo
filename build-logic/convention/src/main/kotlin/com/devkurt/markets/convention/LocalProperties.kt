package com.devkurt.markets.convention

import org.gradle.api.Project
import java.util.Properties

fun Project.localProperty(key: String): String {
    val file = rootProject.layout.projectDirectory.file("local.properties")
    val content = providers.fileContents(file).asText.orNull ?: return ""
    return Properties().apply { load(content.reader()) }.getProperty(key).orEmpty()
}
