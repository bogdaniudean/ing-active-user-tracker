package com.ing.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserSession(
    val username: String,
    val sessionId: String,
    val expiresAt: Long,
)
