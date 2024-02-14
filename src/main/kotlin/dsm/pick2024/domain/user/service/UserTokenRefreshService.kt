package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.user.port.`in`.UserTokenRefreshUseCase
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserTokenRefreshService(
    private val jwtTokenProvider: JwtTokenProvider
) : UserTokenRefreshUseCase {

    @Transactional
    override fun userTokenRefresh(token: String): TokenResponse =
        jwtTokenProvider.reIssue(token)
}
