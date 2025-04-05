plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.kotlin.plugin.spring") version "1.9.25"
    // 플러그인 추가
    id("org.jetbrains.kotlin.plugin.jpa") version "1.9.25"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.innercircle"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // JPA 의존성 추가
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // Validation 의존성 추가
    implementation("org.springframework.boot:spring-boot-starter-validation")
    // ResponseController.kt에서의 javax.validation 사용을 위한 의존성
    // Spring Boot 3.x 이상 사용 시: jakarta.validation
    // Spring Boot 2.x 사용 시: javax.validation
    implementation("jakarta.validation:jakarta.validation-api")
}

kotlin {
    jvmToolchain(17)
    
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
