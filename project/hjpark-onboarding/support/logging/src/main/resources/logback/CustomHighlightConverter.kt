package com.hjpark.shared.logging

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.pattern.color.ANSIConstants
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase

class CustomHighlightConverter : ForegroundCompositeConverterBase<ILoggingEvent>() {
    override fun getForegroundColorCode(event: ILoggingEvent): String {
        return when (event.level.toInt()) {
            Level.ERROR_INT -> ANSIConstants.BOLD + ANSIConstants.RED_FG
            Level.WARN_INT -> ANSIConstants.BOLD + ANSIConstants.YELLOW_FG
            Level.INFO_INT -> ANSIConstants.BOLD + ANSIConstants.GREEN_FG
            Level.DEBUG_INT -> ANSIConstants.BLUE_FG
            Level.TRACE_INT -> ANSIConstants.CYAN_FG
            else -> ANSIConstants.DEFAULT_FG
        }
    }
}