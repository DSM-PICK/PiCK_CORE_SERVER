package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.user.entity.enums.Role
import dsm.pick2024.domain.user.port.`in`.UserTokenRefreshUseCase
import dsm.pick2024.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserTokenRefreshService(
    private val jwtTokenProvider: JwtTokenProvider
) : UserTokenRefreshUseCase {

    @Transactional
    override fun userTokenRefresh(userId: String) =
        jwtTokenProvider.generateToken(userId, Role.STU.toString())
}
