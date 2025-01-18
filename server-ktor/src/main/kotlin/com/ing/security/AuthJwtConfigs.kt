package com.ing.security

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

object AuthJwtConfigs {
    fun installJwtAuthentication(
        application: Application,
        authJwtService: AuthJwtService,
    ) {
        application.install(Authentication) {
            jwt("auth-jwt") {
                realm = "ktor server"
                verifier(authJwtService.verifyToken())
                validate { credential ->
                    val username = credential.payload.getClaim("username").asString()
                    val sessionId = credential.payload.getClaim("sessionId").asString()
                    if (username != null && sessionId != null) {
                        JWTPrincipal(credential.payload)
                    } else null
                }
            }
        }
    }
}