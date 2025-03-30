pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "koodongha-onboarding"
include(":app")
project(":app").projectDir = file("app")

include(":api")
project(":api").projectDir = file("api")

include(":common")
project(":common").projectDir = file("common")
