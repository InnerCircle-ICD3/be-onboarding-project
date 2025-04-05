plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.kotlin.jpa) apply false
    alias(libs.plugins.spring.boot) apply false
}

allprojects {
    group = "com.hjpark.survey"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    // 공통 의존성
    dependencies {
        implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
        implementation(rootProject.libs.spring.cloud.dependencies)
        implementation(rootProject.libs.bundles.kotlin.base)
        implementation(rootProject.libs.jackson.module.kotlin)
        testImplementation(rootProject.libs.bundles.testing)
    }

    // 테스트 설정
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
