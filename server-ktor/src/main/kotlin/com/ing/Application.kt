package com.ing

import com.ing.security.AuthJwtConfigs.installJwtAuthentication
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import com.ing.config.AppConstants
import com.ing.routes.activeUserRoutes
import com.ing.security.authRoutes
import com.ing.security.AuthJwtService
import com.ing.service.UserSessionService

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}


fun Application.module() {
    AppConstants.init(this)
    val sessionManager = UserSessionService()
    val authJwtService = AuthJwtService()

    // Development-only CORS configuration
    install(CORS) {
        allowHost("localhost:5173", schemes = listOf("http"))
        allowHeader("Content-Type")
        allowHeader("Authorization")
        allowMethod(io.ktor.http.HttpMethod.Post)
        allowMethod(io.ktor.http.HttpMethod.Get)
    }

    install(ContentNegotiation) {
        json()
    }

    installJwtAuthentication(this, authJwtService)

    routing {
        authRoutes(authJwtService, sessionManager)
        activeUserRoutes(sessionManager)

    }

    // Start the cleanup job
    launch {
        while (true) {
            sessionManager.cleanupExpiredSessions()
            delay(60_000)
        }
    }
}