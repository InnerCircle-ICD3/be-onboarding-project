package com.innercircle.survey

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SurveyApiApplication

fun main(args: Array<String>) {
	runApplication<SurveyApiApplication>(*args)
}
