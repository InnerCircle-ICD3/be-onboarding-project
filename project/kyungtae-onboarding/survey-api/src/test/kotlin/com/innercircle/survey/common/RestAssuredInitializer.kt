package com.innercircle.survey.common

import io.restassured.RestAssured
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.boot.test.web.server.LocalServerPort


class RestAssuredInitializer : BeforeAllCallback {

    override fun beforeAll(context: ExtensionContext) {
        val testInstance = context.requiredTestInstance
        val portField = testInstance::class.java
            .declaredFields
            .firstOrNull { it.isAnnotationPresent(LocalServerPort::class.java) }

        portField?.let {
            it.isAccessible = true
            val port = it.get(testInstance) as Int
            RestAssured.port = port
        }
    }
}