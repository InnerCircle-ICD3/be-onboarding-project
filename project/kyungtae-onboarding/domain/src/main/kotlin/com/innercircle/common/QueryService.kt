package com.innercircle.common

import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
annotation class QueryService
