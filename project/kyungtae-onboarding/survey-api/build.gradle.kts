plugins {
	kotlin("jvm")
	kotlin("plugin.spring")
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management")
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation(project(":domain"))
	testImplementation("io.rest-assured:rest-assured:5.5.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(testFixtures(project(":domain")))
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.boot:spring-boot-dependencies:3.4.4")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}