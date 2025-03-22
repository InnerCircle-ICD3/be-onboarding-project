plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "sangkyeong-onboarding"
include("bootstrap")
include("presentation")
include("application")
include("infra")
include("domain")
