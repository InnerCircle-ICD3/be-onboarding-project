package com.innercircle.api.common

import org.slf4j.LoggerFactory

interface Log {
    val log
        get() = LoggerFactory.getLogger(this::class.java)
}