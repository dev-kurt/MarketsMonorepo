package com.devkurt.markets.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal const val COMPILE_SDK = 36
internal const val MIN_SDK = 24
internal const val TARGET_SDK = 36

internal const val JVM_TOOLCHAIN = 21
