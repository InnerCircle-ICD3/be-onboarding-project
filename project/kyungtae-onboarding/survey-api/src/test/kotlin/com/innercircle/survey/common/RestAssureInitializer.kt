package com.innercircle.survey.common

import io.restassured.RestAssured
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class RestAssureInitializer : ApplicationListener<ServletWebServerInitializedEvent> {
    override fun onApplicationEvent(event: ServletWebServerInitializedEvent) {
        RestAssured.port = event.webServer.port
        RestAssured.baseURI = "http://localhost"
    }
}