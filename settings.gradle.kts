pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MarketsMonorepo"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":markets")

include(":shared-features:ui:api")
include(":shared-features:ui:di")
include(":shared-features:navigation:api")
include(":shared-features:navigation:di")
include(":shared-features:logger:api")
include(":shared-features:logger:impl")
include(":shared-features:logger:di")
include(":shared-features:network:api")
include(":shared-features:network:impl")
include(":shared-features:network:di")
include(":shared-features:coroutines:di")
include(":markets-features:graph-main:ui:impl")
include(":markets-features:graph-main:di")
include(":markets-features:graph-bottom:ui:api")
include(":markets-features:graph-bottom:ui:impl")
include(":markets-features:graph-bottom:di")
include(":markets-features:graph-dashboard:ui:api")
include(":markets-features:graph-dashboard:ui:impl")
include(":markets-features:graph-dashboard:di")
include(":template-feature:domain:api")
include(":template-feature:domain:impl")
include(":template-feature:data")
include(":template-feature:ui:api")
include(":template-feature:ui:impl")
include(":template-feature:di")
