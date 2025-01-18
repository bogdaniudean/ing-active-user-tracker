package com.ing.service

import kotlinx.coroutines.flow.Flow


class UserSessionServiceRedis() {

    private val activeUserFlow = null /* Reactive flow setup */

    /*
        Use redis pub/sub. Publish updates after each operation. Listen for updates and notify the flow.
     */

    fun addSession(username: String) {
        // Save session in Redis with TTL
        // Update reactive flow
    }


    fun refreshSession(username: String) {
        // Update session TTL in Redis
    }

    fun removeSession(username: String) {
        // Remove session key from Redis
        // Update reactive flow
    }

    fun cleanupExpiredSessions() {
        // Check all session keys and clean up any invalid ones
        // THis needs to be managed by an external instance, not these auth servers.
    }


    fun getActiveSessionCount(): Int {
        // Return count of active Redis keys
        return 0
    }


    private fun updateActiveUserCount() {
        // Broadcast current user count to reactive flow
    }


    fun getActiveUserFlow(): Flow<Int>? {
        // Return flow of user count updates
        return null
    }
}
