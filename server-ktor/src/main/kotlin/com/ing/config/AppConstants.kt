package com.ing.config

import io.ktor.server.application.*
import io.ktor.server.config.*

object AppConstants {
    val SESSION_MAX_AGE_MS: Long by lazy {
        config.property("ktor.session.maxAgeMs").getString().toLong()
    }

    val CLEANUP_INTERVAL_MS: Long by lazy {
        config.property("ktor.cleanup.intervalMs").getString().toLong()
    }
    private lateinit var config: ApplicationConfig

    fun init(application: Application) {
        config = application.environment.config
    }
}
