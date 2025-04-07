package com.innercircle.presurveyapi.common.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> T.logger(): Logger = LoggerFactory.getLogger(T::class.java)