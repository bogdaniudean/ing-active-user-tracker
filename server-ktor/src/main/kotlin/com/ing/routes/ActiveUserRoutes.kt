package com.ing.routes

import com.ing.service.UserSessionService
import io.ktor.http.ContentType
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.activeUserRoutes(sessionManager: UserSessionService) {
    get("/active-users-stream") {
        call.response.headers.append("Content-Type", "text/event-stream")

        call.respondTextWriter(contentType = ContentType.Text.EventStream) {
            try {
                // Send the current active user count immediately
                write("data: Active Users: ${sessionManager.getActiveSessionCount()}\n\n")
                flush()

                // Then start collecting updates from the shared flow
                sessionManager.getActiveUserFlow().collect { count ->
                    write("data: Active Users: $count\n\n")
                    flush()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}