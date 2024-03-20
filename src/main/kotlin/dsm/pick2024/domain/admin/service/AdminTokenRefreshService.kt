package dsm.pick2024.domain.admin.service

import dsm.pick2024.domain.admin.port.`in`.AdminTokenRefreshUseCase
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import dsm.pick2024.global.security.jwt.dto.TokenResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminTokenRefreshService(
    private val jwtTokenProvider: JwtTokenProvider
) : AdminTokenRefreshUseCase {

    @Transactional
    override fun adminTokenRefresh(token: String): TokenResponse =
        jwtTokenProvider.reIssue(token)
}
