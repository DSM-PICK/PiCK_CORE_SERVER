package dsm.pick2024.global.security.jwt

import dsm.pick2024.domain.user.entity.enums.Role
import dsm.pick2024.global.security.auth.AdminDetailsService
import dsm.pick2024.global.security.auth.AuthDetailsService
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import dsm.pick2024.global.security.jwt.entity.RefreshToken
import dsm.pick2024.global.security.jwt.entity.repository.RefreshTokenRepository
import dsm.pick2024.global.security.jwt.exception.ExpiredTokenException
import dsm.pick2024.global.security.jwt.exception.InvalidJwtException
import io.jsonwebtoken.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
    private val authDetailsService: AuthDetailsService,
    private val adminDetailsService: AdminDetailsService,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    companion object {
        private const val ACCESS_KEY = "access_token"
        private const val REFRESH_KEY = "refresh_token"
    }

    fun generateToken(userId: String, role: String): TokenResponse {
        val accessToken = generateAccessToken(userId, role, ACCESS_KEY, jwtProperties.accessExp)
        val refreshToken = generateRefreshToken(role, REFRESH_KEY, jwtProperties.refreshExp)
        refreshTokenRepository.save(
            RefreshToken(userId, refreshToken, jwtProperties.refreshExp)
        )
        return TokenResponse(accessToken, refreshToken)
    }

//    fun reIssue(refreshToken: String): TokenResponse {
//        if (!isRefreshToken(refreshToken)) {
//            throw InvalidJwtException
//        }
//
//        refreshTokenRepository.findByToken(refreshToken)
//            ?.let { token ->
//                val id = token.id
//                val role = getRole(token.token)
//
//                val tokenResponse = generateToken(id, role)
//                token.update(tokenResponse.refreshToken, jwtProperties.refreshExp)
//                return TokenResponse(tokenResponse.accessToken, tokenResponse.refreshToken)
//            } ?: throw InvalidJwtException
//    }
//
//    fun getRole(token: String) = getJws(token).body["role"].toString()
//
//    private fun isRefreshToken(token: String?): Boolean {
//        return REFRESH_KEY == getJws(token!!).header["typ"].toString()
//    }

    private fun generateAccessToken(id: String, role: String, type: String, exp: Long): String =
        Jwts.builder()
            .setSubject(id)
            .setHeaderParam("typ", type)
            .claim("role", role)
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey)
            .setExpiration(Date(System.currentTimeMillis() + exp * 1000))
            .setIssuedAt(Date())
            .compact()

    private fun generateRefreshToken(role: String, type: String, exp: Long): String =
        Jwts.builder()
            .setHeaderParam("typ", type)
            .claim("role", role)
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey)
            .setExpiration(Date(System.currentTimeMillis() + exp * 1000))
            .setIssuedAt(Date())
            .compact()

    fun resolveToken(request: HttpServletRequest): String? =
        request.getHeader(jwtProperties.header)?.also {
            if (it.startsWith(jwtProperties.prefix)) {
                return it.substring(jwtProperties.prefix.length)
            }
        }

    fun authentication(token: String): Authentication? {
        val body: Claims = getJws(token).body
        val userDetails: UserDetails = getDetails(body)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun getJws(token: String): Jws<Claims> {
        return try {
            Jwts.parser().setSigningKey(jwtProperties.secretKey).parseClaimsJws(token)
        } catch (e: ExpiredJwtException) {
            throw ExpiredTokenException
        } catch (e: Exception) {
            throw InvalidJwtException
        }
    }

    private fun getDetails(body: Claims): UserDetails {
        return if (Role.STU.toString() == body["role"].toString()) {
            authDetailsService.loadUserByUsername(body.subject)
        } else {
            adminDetailsService.loadUserByUsername(body.subject)
        }
    }
}
