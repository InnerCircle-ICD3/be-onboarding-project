package com.innercircle.presurveyapi.config


import com.innercircle.presurveyapi.common.logging.MdcFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.boot.web.servlet.FilterRegistrationBean

@Configuration
class AppConfig {

    @Bean
    fun logFilter(): FilterRegistrationBean<MdcFilter> {
        val registration = FilterRegistrationBean(MdcFilter())
        registration.order = 1
        return registration
    }
}