package dsm.pick2024.global.security.jwt

import dsm.pick2024.global.security.auth.AuthDetailsService
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import dsm.pick2024.global.security.jwt.entity.RefreshToken
import dsm.pick2024.global.security.jwt.entity.repository.RefreshTokenRepository
import dsm.pick2024.global.security.jwt.exception.ExpiredTokenException
import dsm.pick2024.global.security.jwt.exception.InvalidJwtException
import io.jsonwebtoken.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
    private val authDetailsService: AuthDetailsService,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    companion object {
        private const val ACCESS_KEY = "access_token"
        private const val REFRESH_KEY = "refresh_token"
    }

    fun getToken(name: String, role: String): TokenResponse {
        val accessToken: String = generateAccessToken(name, role, ACCESS_KEY, jwtProperties.accessExp)
        val refreshToken: String = generateRefreshToken(REFRESH_KEY, role,jwtProperties.refreshExp)
        refreshTokenRepository.save(
            RefreshToken(name, refreshToken, jwtProperties.refreshExp)
        )
        return TokenResponse(accessToken = accessToken, refreshToken = refreshToken)
    }

    private fun generateAccessToken(name: String, role:String, type: String, expiration: Long): String {
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey)
            .setSubject(name)
            .setHeaderParam("type", type)
            .claim("role", role)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiration * 1000))
            .compact()
    }

    private fun generateRefreshToken(role: String, type: String, ttl: Long): String {
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey)
            .setHeaderParam("type", type)
            .claim("role", role)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + ttl * 1000))
            .compact()
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val bearer: String? = request.getHeader("Authorization")

        return parseToken(bearer)
    }

    fun parseToken(bearerToken: String?): String? {
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replace("Bearer ", "")
        } else {
            null
        }
    }

    fun authorization(token: String): UsernamePasswordAuthenticationToken {
        return token.let {
            val userDetails: UserDetails = authDetailsService.loadUserByUsername(getTokenSubject(token))
            return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
        }
    }

    private fun getTokenSubject(subject: String): String {
        return getTokenBody(subject).subject
    }

    private fun getTokenBody(token: String?): Claims {
        return try {
            Jwts.parser().setSigningKey(jwtProperties.secretKey)
                .parseClaimsJws(token).body
        } catch (e: Exception) {
            when (e) {
                is ExpiredJwtException -> throw ExpiredTokenException
                is InvalidClaimException -> throw InvalidJwtException
                else -> throw InvalidJwtException
            }
        }
    }
}
