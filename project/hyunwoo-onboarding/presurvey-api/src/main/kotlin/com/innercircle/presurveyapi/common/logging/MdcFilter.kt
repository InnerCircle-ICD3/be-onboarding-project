package com.innercircle.presurveyapi.common.logging

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.MDC
import java.util.UUID

class MdcFilter : Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        try {
            val http = request as HttpServletRequest
            MDC.put("traceId", UUID.randomUUID().toString())
            MDC.put("method", http.method)
            MDC.put("uri", http.requestURI)
            chain.doFilter(request, response)
        } finally {
            MDC.clear()
        }
    }
}