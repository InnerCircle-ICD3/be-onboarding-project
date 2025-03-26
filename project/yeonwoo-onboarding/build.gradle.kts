import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_19
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Spring Boot Web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // JSON 처리
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // ✅ DB 관련 설정 (H2 & MySQL)
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j") // MySQL 드라이버 (선택)

    // Lombok (선택적)
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // ✅ JUnit 5 설정 (테스트 관련)
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine") // JUnit 4 제외
    }
    // testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "19"
    }
}

tasks.withType<Test> {
    useJUnitPlatform() // ✅ JUnit 5 플랫폼 사용 설정
}
