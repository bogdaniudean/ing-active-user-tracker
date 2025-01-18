
package com.ing.service

import com.ing.config.AppConstants
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.util.concurrent.ConcurrentHashMap

class UserSessionService {

    // Shared flow for broadcasting active user count
    private val activeUserFlow = MutableSharedFlow<Int>(
        replay = 1, // Replays the latest value for new subscribers
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    // Tracks active sessions using a combination of username and session ID
    private val activeSessions = ConcurrentHashMap<String, MutableMap<String, Long>>()

    /**
     * Adds a new session for a user.
     * `sessionId` should be unique for each user-session combination (e.g., device-specific UUID).
     */
    fun addSession(username: String, sessionId: String) {
        val userSessions = activeSessions.computeIfAbsent(username) { ConcurrentHashMap() }
        userSessions[sessionId] = System.currentTimeMillis()
        emitActiveUserCount()
    }

    /**
     * Refreshes an existing session for a user.
     */
    fun refreshSession(username: String, sessionId: String) {
        activeSessions[username]?.let { sessions ->
            sessions[sessionId] = System.currentTimeMillis()
        }
    }

    /**
     * Removes a specific session for a user.
     */
    fun removeSession(username: String, sessionId: String) {
        activeSessions[username]?.let { sessions ->
            sessions.remove(sessionId)
            if (sessions.isEmpty()) {
                activeSessions.remove(username)
            }
            emitActiveUserCount()
        }
    }

    /**
     * Cleans up expired sessions.
     */
    fun cleanupExpiredSessions() {
        val now = System.currentTimeMillis()
        activeSessions.entries.removeIf { (_, sessions) ->
            sessions.entries.removeIf { (_, timestamp) ->
                val expired = now - timestamp > AppConstants.JWT_TOKEN_VALIDITY_IN_MS
                if (expired) println("Session expired for session ID")
                expired
            }
            sessions.isEmpty()
        }
        emitActiveUserCount()
    }

    /**
     * Returns the current number of active sessions across all users.
     */
    fun getActiveSessionCount(): Int = activeSessions.size

    /**
     * Emits the active user count to the shared flow.
     */
    private fun emitActiveUserCount() {
        activeUserFlow.tryEmit(getActiveSessionCount())
    }

    /**
     * Returns the shared flow for active user count updates.
     */
    fun getActiveUserFlow(): SharedFlow<Int> = activeUserFlow

}
