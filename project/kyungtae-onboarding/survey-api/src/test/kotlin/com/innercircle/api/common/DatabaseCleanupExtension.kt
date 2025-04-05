package com.innercircle.api.common

import com.innercircle.domain.common.DatabaseCleaner
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.junit.jupiter.SpringExtension

class DatabaseCleanupExtension : AfterEachCallback {

    override fun afterEach(context: ExtensionContext?) {
        val applicationContext = SpringExtension.getApplicationContext(context!!)
        val cleaner = applicationContext.getBean(DatabaseCleaner::class.java)
        cleaner.truncate()
    }
}