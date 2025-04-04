dependencies {
   api("org.springframework.boot:spring-boot-starter-logging")
   api("net.logstash.logback:logstash-logback-encoder:7.3")
}

// 리소스 번들링
tasks.jar {
   from("src/main/resources") {
      into("META-INF/resources/")
   }
}