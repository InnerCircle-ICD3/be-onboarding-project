package com.survey.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(
        scanBasePackages = {"com.survey"}
)
@EntityScan(basePackages = "com.survey.domain")
public class SurveyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurveyApiApplication.class, args);
    }
}
