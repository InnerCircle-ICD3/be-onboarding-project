plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("plugin.jpa")
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation ("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")
    implementation("org.fusesource.jansi:jansi:2.4.0")

    runtimeOnly("com.h2database:h2")

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

tasks.bootRun {
    enabled = false
}

