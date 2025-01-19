package com.ing.service

import com.ing.dto.UserSession
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.util.concurrent.ConcurrentHashMap

class UserSessionService {

    private val activeUserFlow = MutableSharedFlow<Int>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    // Key: username, Value: Map of sessionId → UserSession
    private val activeSessions = ConcurrentHashMap<String, MutableMap<String, UserSession>>()

    /**
     * Adds a new session for a user.
     */
    fun addSession(userSession: UserSession) {
        val userSessions = activeSessions.computeIfAbsent(userSession.username) { mutableMapOf() }
        userSessions[userSession.sessionId] = userSession
        emitActiveUserCount()
    }

    /**
     * Checks if a specific session is expired.
     */
    private fun isSessionExpired(userSession: UserSession): Boolean {
        return System.currentTimeMillis() > userSession.expiresAt
    }

    /**
     * Validates if a session exists and is not expired.
     */
    fun isSessionValid(username: String, sessionId: String): Boolean {
        val userSessions = activeSessions[username] ?: return false
        val session = userSessions[sessionId] ?: return false
        return !isSessionExpired(session)
    }

    /**
     * Removes a specific session for a user.
     */
    fun removeSession(username: String, sessionId: String) {
        activeSessions[username]?.let { userSessions ->
            userSessions.remove(sessionId)
            if (userSessions.isEmpty()) {
                activeSessions.remove(username)
            }
            emitActiveUserCount()
        }
    }

    /**
     * Cleans up all expired sessions for all users.
     */
    fun cleanupExpiredSessions() {
        activeSessions.entries.removeIf { (_, userSessions) ->
            userSessions.entries.removeIf { (_, session) ->
                val expired = isSessionExpired(session)
                if (expired) println("Session expired for session ID: ${session.sessionId}")
                expired
            }
            userSessions.isEmpty()
        }
        emitActiveUserCount()
    }

    /**
     * Gets the total count of active users.
     */
    fun getActiveSessionCount(): Int {
        return activeSessions.values.size
    }

    /**
     * Broadcasts the active user count to the shared flow.
     */
    private fun emitActiveUserCount() {
        activeUserFlow.tryEmit(getActiveSessionCount())
    }

    /**
     * Returns the shared flow for active user count updates.
     */
    fun getActiveUserFlow(): SharedFlow<Int> = activeUserFlow

}
