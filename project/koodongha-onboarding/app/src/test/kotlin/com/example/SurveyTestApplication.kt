package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SurveyTestApplication

fun main(args: Array<String>) {
    runApplication<SurveyTestApplication>(*args)
}