import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
    apply(plugin = rootProject.libs.plugins.kotlin.jvm.get().pluginId)
    apply(plugin = rootProject.libs.plugins.kotlin.spring.get().pluginId)
    apply(plugin = rootProject.libs.plugins.spring.boot.get().pluginId)
    
    // 공통 의존성
    dependencies {
        implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
        implementation(rootProject.libs.spring.cloud.dependencies)
        implementation(rootProject.libs.bundles.kotlin.base)
        implementation(rootProject.libs.jackson.module.kotlin)
        testImplementation(rootProject.libs.bundles.testing)
    }
    tasks.named<KotlinCompile>("compileKotlin") {
        compilerOptions {
            freeCompilerArgs.add("-Xjsr305=strict")
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)

        }
    }

    if (project.name != "api") {
        tasks.getByName("bootJar") {
            enabled = false
        }
        tasks.jar {
            enabled = false
        }
    }

    // 테스트 설정
    tasks.named<Test>("test") {
        useJUnitPlatform()
    }

}