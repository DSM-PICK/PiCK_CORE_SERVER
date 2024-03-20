package dsm.pick2024.domain.user.port.`in`

import dsm.pick2024.global.security.jwt.dto.TokenResponse

interface UserTokenRefreshUseCase {
    fun userTokenRefresh(accountId: String): TokenResponse
}
