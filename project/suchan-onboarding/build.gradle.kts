buildscript {
    extra.apply {
        set("springBootVersion", "3.4.3")
        set("springDependencyManagementVersion", "1.1.7")
        set("javaVersion", JavaVersion.VERSION_21)

        set("lombokVersion", "1.18.32")
        set("junitVersion", "5.10.2")
    }
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${property("springBootVersion")}")
        classpath("io.spring.gradle:dependency-management-plugin:${property("springDependencyManagementVersion")}")
    }
}

plugins {
    java
}

allprojects {
    group = "com.survey"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    configure<JavaPluginExtension> {
        val javaVersion = project.property("javaVersion") as JavaVersion
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    dependencies {
        // spring
        implementation("org.springframework.boot:spring-boot-starter")

        // lombok
        compileOnly("org.projectlombok:lombok:${project.property("lombokVersion")}")
        annotationProcessor("org.projectlombok:lombok:${project.property("lombokVersion")}")

        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
        enabled = false
    }

    tasks.getByName<Jar>("jar") {
        enabled = true
    }

    if (project.name == "application") {
        tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
            enabled = true
        }
        tasks.getByName<Jar>("jar") {
            enabled = false
        }
    }

    if (project.name == "domain" || project.name == "common") {
        tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
            enabled = false
        }
        tasks.getByName<Jar>("jar") {
            enabled = true
        }
    }
}