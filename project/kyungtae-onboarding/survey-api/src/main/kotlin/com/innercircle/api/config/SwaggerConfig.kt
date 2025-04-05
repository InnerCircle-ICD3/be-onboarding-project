package com.innercircle.api.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Survey API 문서")
                    .description("설문 관련 API 명세서입니다.")
                    .version("v1.0.0")
            )
            .addServersItem(
                io.swagger.v3.oas.models.servers.Server()
                    .url("http://localhost:8080")
                    .description("로컬 서버")
            )
    }

    @Bean
    fun publicApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("survey-api")
            .packagesToScan("com.innercircle.api.survey")
            .build()
    }
}