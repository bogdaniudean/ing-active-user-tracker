package com.ing.security

import com.ing.dto.LoginRequest
import com.ing.service.UserSessionService
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes(authJwtService: AuthJwtService, sessionManager: UserSessionService) {
    post("/login") {
        val requestBody = call.receive<LoginRequest>()
        val username = requestBody.username
        val password = requestBody.password

        if (username != null && password != null && username == password) {
            val sessionId = java.util.UUID.randomUUID().toString()
            val token = authJwtService.generateToken(username, sessionId)
            sessionManager.addSession(username, sessionId)
            call.respond(mapOf("token" to token))
        } else {
            call.respondText("Invalid credentials", status = io.ktor.http.HttpStatusCode.Unauthorized)
        }
    }

    // Authenticated routes
    authenticate("auth-jwt") {
        post("/logout") {
            val principal = call.principal<JWTPrincipal>()
            val sessionId = principal!!.payload.getClaim("sessionId").asString()
            val username = principal!!.payload.getClaim("username").asString()
            sessionManager.removeSession(username, sessionId)
            call.respondText("Logged out")
        }

        // not used, for now, but necessary for a comfortable jwt based auth
        // also requires a refresh token for a full implementation
        // left in place for showcasing
        post("/refresh") {
            val principal = call.principal<JWTPrincipal>()
            val sessionId = principal!!.payload.getClaim("sessionId").asString()
            val username = principal!!.payload.getClaim("username").asString()
            sessionManager.refreshSession(username, sessionId)
            call.respondText("Logged out")
        }
    }
}