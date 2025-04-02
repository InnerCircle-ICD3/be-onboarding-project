dependencies {
    implementation(project(":onboarding-data"))
    implementation(project(":onboarding-cloud"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}