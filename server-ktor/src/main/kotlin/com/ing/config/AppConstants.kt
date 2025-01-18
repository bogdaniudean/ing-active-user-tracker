package com.ing.config

import io.ktor.server.application.*
import io.ktor.server.config.*

object AppConstants {
    val JWT_TOKEN_VALIDITY_IN_MS: Long by lazy {
        config.property("ktor.jwt.tokenValidityInMs").getString().toLong()
    }
    val JWT_SECRET: String by lazy {
        config.property("ktor.jwt.secret").getString()
    }
    val JWT_ISSUER: String by lazy {
        config.property("ktor.jwt.issuer").getString()
    }
    val JWT_AUDIENCE: String by lazy {
        config.property("ktor.jwt.audience").getString()
    }

    private lateinit var config: ApplicationConfig

    fun init(application: Application) {
        config = application.environment.config
    }
}
