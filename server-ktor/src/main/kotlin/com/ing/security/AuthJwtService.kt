package com.ing.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.ing.config.AppConstants
import java.util.Date

class AuthJwtService {
    private val algorithm = Algorithm.HMAC256(AppConstants.JWT_SECRET)

    fun generateToken(username: String, sessionId: String): String {
        return JWT.create()
            .withIssuer(AppConstants.JWT_ISSUER)
            .withAudience(AppConstants.JWT_AUDIENCE)
            .withClaim("username", username)
            .withClaim("sessionId", sessionId)
            .withExpiresAt(Date(System.currentTimeMillis() + AppConstants.JWT_TOKEN_VALIDITY_IN_MS))
            .sign(algorithm)
    }

    fun verifyToken() = JWT.require(algorithm)
        .withIssuer(AppConstants.JWT_ISSUER)
        .withAudience(AppConstants.JWT_AUDIENCE)
        .build()
}