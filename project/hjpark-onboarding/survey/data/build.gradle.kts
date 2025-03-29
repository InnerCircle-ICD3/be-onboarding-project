dependencies {
    implementation(project(":survey:domain"))
    // JPA & Database
    implementation(libs.spring.boot.starter.data.jpa)
    runtimeOnly(libs.h2)
}
