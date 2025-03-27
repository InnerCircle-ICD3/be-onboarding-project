package com.innercircle.survey.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

fun jsonMapper(): ObjectMapper = jacksonObjectMapper()
    .registerKotlinModule()
    .registerModule(JavaTimeModule())