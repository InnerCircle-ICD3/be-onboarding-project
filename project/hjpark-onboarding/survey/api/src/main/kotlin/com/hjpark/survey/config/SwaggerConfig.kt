package com.hjpark.survey.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .components(Components())
        .info(apiInfo())
        .addServersItem(
            Server().url("/").description("현재 실행 중인 서버의 루트 URL 사용")
        )


    private fun apiInfo(): Info {
        return Info()
            .title("설문조사 서비스 API")
            .description("Onboarding project: 설문조사 서비스 API Documentation")
            .version("1.0.0")
    }
} 