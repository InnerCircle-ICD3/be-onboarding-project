dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("com.h2database:h2")

    implementation(project(":suchan-onboarding:common"))
    implementation(project(":suchan-onboarding:domain"))
    testImplementation(testFixtures(project(":suchan-onboarding:domain")))
}