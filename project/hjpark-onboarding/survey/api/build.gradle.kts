tasks.bootJar {
    enabled = true
}

dependencies {
    implementation(project(":survey:application"))
    implementation(project(":support:shared-common"))
    implementation(project(":support:logging"))
    implementation(libs.spring.boot.starter.web)
    implementation(libs.springdoc.openapi)
    implementation(libs.micrometer.tracing)
    testImplementation(libs.bundles.testing)
}
