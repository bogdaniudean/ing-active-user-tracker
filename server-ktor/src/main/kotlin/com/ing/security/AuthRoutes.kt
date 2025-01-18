package com.ing.security

import com.ing.config.AppConstants
import com.ing.dto.LoginRequest
import com.ing.dto.UserSession
import com.ing.service.UserSessionService
import io.ktor.server.auth.*
import io.ktor.server.sessions.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.UUID;

fun Route.authRoutes(sessionManager: UserSessionService) {
    post("/login") {
        val requestBody = call.receive<LoginRequest>()
        val username = requestBody.username
        val password = requestBody.password

        if (username != null && password != null && username == password) {
            val sessionId = UUID.randomUUID().toString()
            val expiresAt = System.currentTimeMillis() + AppConstants.SESSION_MAX_AGE_MS
            val newUserSession = UserSession(username, sessionId, expiresAt)

            call.sessions.set(newUserSession)
            sessionManager.addSession(newUserSession)
            call.respondText("Login successful")
        } else {
            call.respondText("Invalid credentials", status = io.ktor.http.HttpStatusCode.Unauthorized)
        }
    }

    // Authenticated routes
    authenticate("auth-session") {
        post("/logout") {
            val session = call.sessions.get<UserSession>()
            if (session != null) {
                sessionManager.removeSession(session.username, session.sessionId)
                call.sessions.clear<UserSession>()
                call.respondText("Logged out")
            } else {
                call.respondText("No active session", status = io.ktor.http.HttpStatusCode.BadRequest)
            }
        }
    }
}
