package dsm.pick2024_server.global.security.jwt.dto

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
