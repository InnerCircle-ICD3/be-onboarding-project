import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":presentation"))
    implementation(project(":application"))
    implementation(project(":infra"))
    implementation(project(":domain"))
}

tasks.withType<BootJar> {
    enabled = true
}

tasks.withType<Jar> {
    enabled = false
}