plugins {
    java
    `java-test-fixtures`
}

dependencies {
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation(project(":common"))
}