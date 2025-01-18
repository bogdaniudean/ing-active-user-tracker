package com.ing.security

import com.ing.dto.UserSession
import com.ing.service.UserSessionService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.sessions.*

object AuthConfigs {
}

fun installSessionBasedAuthentication(application: Application, sessionManager: UserSessionService) {

    application.install(Sessions) {
        cookie<UserSession>("USER_SESSION") {
            cookie.path = "/"
            cookie.httpOnly = true
            cookie.maxAgeInSeconds = com.ing.config.AppConstants.SESSION_MAX_AGE_MS / 1000
        }
    }

    application.install(Authentication) {
        session<UserSession>("auth-session") {
            validate { session ->
                val isValid = sessionManager.isSessionValid(session.username, session.sessionId)

                if (isValid) {
                    session
                } else {
                    null
                }
            }
        }
    }
}
