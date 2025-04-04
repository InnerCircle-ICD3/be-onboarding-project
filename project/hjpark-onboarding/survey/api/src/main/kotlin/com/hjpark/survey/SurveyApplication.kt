package com.hjpark.survey

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
class SurveyApplication

fun main(args: Array<String>) {
    runApplication<SurveyApplication>(*args)
} 